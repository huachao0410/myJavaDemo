package org.jasig.cas.client.authentication;

import cn.com.bmsoft.base.common.util.StringUtil;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.validation.Assertion;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationFilterExtend extends AbstractCasFilter {
    private String casServerLoginUrl;
    private boolean renew = false;
    private boolean gateway = false;
    private GatewayResolver gatewayStorage = new DefaultGatewayResolverImpl();

    //路径白名单,名单中的路径不经过单点登录限制
    private List<String> ignoreUrls = new ArrayList<String>();//需要排除的路径

    public AuthenticationFilterExtend() {
    }

    protected void initInternal(FilterConfig filterConfig) throws ServletException {
        if (!this.isIgnoreInitConfiguration()) {
            super.initInternal(filterConfig);
            this.setCasServerLoginUrl(this.getPropertyFromInitParams(filterConfig, "casServerLoginUrl", (String) null));
            this.log.trace("Loaded CasServerLoginUrl parameter: " + this.casServerLoginUrl);
            this.setRenew(this.parseBoolean(this.getPropertyFromInitParams(filterConfig, "renew", "false")));
            this.log.trace("Loaded renew parameter: " + this.renew);
            this.setGateway(this.parseBoolean(this.getPropertyFromInitParams(filterConfig, "gateway", "false")));
            this.log.trace("Loaded gateway parameter: " + this.gateway);
            String gatewayStorageClass = this.getPropertyFromInitParams(filterConfig, "gatewayStorageClass", (String) null);
            if (gatewayStorageClass != null) {
                try {
                    this.gatewayStorage = (GatewayResolver) Class.forName(gatewayStorageClass).newInstance();
                } catch (Exception var4) {
                    this.log.error(var4, var4);
                    throw new ServletException(var4);
                }
            }
            //获取web.xml中定义好的排除路径列表
            String[] paths = getPropertyFromInitParams(filterConfig, "ignoreUrls", null).split(",");
            for(String path : paths){
                if(CommonUtils.isNotBlank(path)) {
                    ignoreUrls.add(path.trim());
                }
            }
        }

    }

    public void init() {
        super.init();
        CommonUtils.assertNotNull(this.casServerLoginUrl, "casServerLoginUrl cannot be null.");
    }

    public final void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        Assertion assertion = session != null ? (Assertion) session.getAttribute("_const_cas_assertion_") : null;
        if (assertion != null) {
            filterChain.doFilter(request, response);
        } else {
            String serviceUrl = this.constructServiceUrl(request, response);
            boolean isInWhiteList = false;
            if (ignoreUrls != null && ignoreUrls.size() > 0 && serviceUrl != null) {
                for (String path : ignoreUrls) {
                    if(path.length() < 2) {
                        break;
                    }
                    if (CommonUtils.isNotBlank(path)) {
                        isInWhiteList = StringUtil.isIn(path, serviceUrl);
                        if (isInWhiteList) {
                            break;
                        }
                    }
                }
            }

            String ticket = CommonUtils.safeGetParameter(request, this.getArtifactParameterName());
            boolean wasGatewayed = this.gatewayStorage.hasGatewayedAlready(request, serviceUrl);
            if (isInWhiteList) {
                filterChain.doFilter(request, response);
            } else if (!CommonUtils.isNotBlank(ticket) && !wasGatewayed) {
                this.log.debug("no ticket and no assertion found");
                String modifiedServiceUrl;
                if (this.gateway) {
                    this.log.debug("setting gateway attribute in session");
                    modifiedServiceUrl = this.gatewayStorage.storeGatewayInformation(request, serviceUrl);
                } else {
                    modifiedServiceUrl = serviceUrl;
                }

                if (this.log.isDebugEnabled()) {
                    this.log.debug("Constructed service url: " + modifiedServiceUrl);
                }

                String urlToRedirectTo = CommonUtils.constructRedirectUrl(this.casServerLoginUrl, this.getServiceParameterName(), modifiedServiceUrl, this.renew, this.gateway);
                if (this.log.isDebugEnabled()) {
                    this.log.debug("redirecting to \"" + urlToRedirectTo + "\"");
                }

                response.sendRedirect(urlToRedirectTo);
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }

    public final void setRenew(boolean renew) {
        this.renew = renew;
    }

    public final void setGateway(boolean gateway) {
        this.gateway = gateway;
    }

    public final void setCasServerLoginUrl(String casServerLoginUrl) {
        this.casServerLoginUrl = casServerLoginUrl;
    }

    public final void setGatewayStorage(GatewayResolver gatewayStorage) {
        this.gatewayStorage = gatewayStorage;
    }
}

