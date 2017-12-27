package cn.com.bmsoft.base.security;

import cn.com.bmsoft.base.common.security.SecurityInvokeMetadataSource;
import cn.com.bmsoft.base.common.security.UserInfo;
import cn.com.bmsoft.services.user.IWechatUserService;
import cn.com.bmsoft.services.wechat.IWechatOauthService;
import cn.com.bmsoft.weixin.service.IOauthService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Spring触发的权限拦截器（微信处理）
 *
 */
public class WeixinSecurityFilter extends AbstractSecurityInterceptor implements Filter {

    @Autowired
    private IWechatUserService wechatUserService;

    @Autowired
    private SecurityInvokeMetadataSource securityMetadataSource;
    
    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private IWechatOauthService wechatOauthService;
    
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private String loginFormUrl;
    private boolean useForward = false;
    private List<String> ingoredTargetUrls = new ArrayList<String>();

    private String userToken;
    private String appid;
    private String appsecret;

    public SecurityInvokeMetadataSource getSecurityMetadataSource() {
        return securityMetadataSource;
    }

    public void setSecurityMetadataSource(SecurityInvokeMetadataSource securityMetadataSource) {
        this.securityMetadataSource = securityMetadataSource;
    }

    @Override
    public Class<? extends Object> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        FilterInvocation filterInvocation = new FilterInvocation(request, response, chain);
        this.invoke(filterInvocation);
    }

    private void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {
        HttpServletRequest request = filterInvocation.getRequest();
        HttpServletResponse response = filterInvocation.getHttpResponse();

        //获取访问路径
        ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(requestAttributes);

        //filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());

        //获取用户权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            //未登录用户-访问登录页面直接执行
            if (request.getRequestURI().equals(request.getContextPath() + this.loginFormUrl)) {
                filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
                return;
            }

            //未登录用户-访问其它页面自动跳转到登录页面
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            String jumpUrl = this.getTargetUrl(request);

            if (this.useForward) {
                RequestDispatcher dispatcher = request.getRequestDispatcher(jumpUrl);
                dispatcher.forward(request, response);
            } else {
                this.redirectStrategy.sendRedirect(request, response, jumpUrl);
            }
        } else {
            UserInfo userInfo = (UserInfo) authentication.getDetails();
            // 使用微信openid登录
            if(userInfo.getOpenid() != null && userInfo.getOpenid().equals(userInfo.getUsername())) {
                String jumpUrl = this.getTargetLoginUrl(request, userInfo.getOpenid());
                this.redirectStrategy.sendRedirect(request, response, jumpUrl);
            }

            //已登录用户-访问登录页面，直接跳转至首页
            if (request.getRequestURI().equals(request.getContextPath() + this.loginFormUrl)) {
                this.successHandler.onAuthenticationSuccess(request, response, authentication);
                return;
            }

            //否则执行验证
            InterceptorStatusToken token = super.beforeInvocation(filterInvocation);
            try {
                filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
            } finally {
                super.afterInvocation(token, null);
            }
        }


        /*
        //获取用户权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            //未登录用户-访问登录页面直接执行
            if (request.getRequestURI().equals(request.getContextPath() + this.loginFormUrl)) {
                filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
                return;
            }

            //微信特有处理：判断地址是否带有openid，如果有，通过用户中心服务获取用户信息

            String openid = request.getParameter("openid");
            String code = request.getParameter("code");

            // openid为空或者无值时
            if(StringUtils.isBlank(openid)){
                if(StringUtils.isBlank(code)){
                    StringBuffer url = request.getRequestURL();
                    if(request.getQueryString() != null) {
                        url.append("?" + request.getQueryString());
                    }
                    String getCodeRedirectUrl = this.wechatOauthService.getCodeRedirectUrl(url.toString());
                    this.redirectStrategy.sendRedirect(request, response, getCodeRedirectUrl); //重定向获取微信code
                }else{
                    String token;
                    try {
                        token = this.wechatOauthService.getAccessToken();
                        logger.error("!!!GET TOKEN BY CODE:"+token);
                        JSONObject obj = JSONObject.parseObject(token);
                        openid = obj.get("openid")+"";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            // 再次判断openid是否有值
            if(StringUtils.isNotBlank(openid)) {
            	Map<String, Object> userMap = this.wechatUserService.getUser(openid);
    			if(userMap != null) {
    				//获取用户信息
    				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userMap.get("useraccount"), "");
                    UserInfo userInfo = createUserInfo(userMap, request);
    	            authenticationToken.setDetails(userInfo);
    	            authentication = super.getAuthenticationManager().authenticate(authenticationToken);
    	            //写到会话
    	            SecurityContextHolder.getContext().setAuthentication(authentication);

                    //将security context存放在session中，用于用户手动登出时，置空会话
                    HttpSession session=request.getSession(true);
                    session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
    	            
    	            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
                    return;
    			} else {
                    SecurityContextHolder.getContext().setAuthentication(null);
                    //未登录用户-访问其它页面自动跳转到登录页面
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    String jumpUrl = this.getTargetUrl(request);

                    if (this.useForward) {
                        RequestDispatcher dispatcher = request.getRequestDispatcher(jumpUrl);
                        dispatcher.forward(request, response);
                    } else {
                        this.redirectStrategy.sendRedirect(request, response, jumpUrl);
                    }
                }
            }
        } else {
            //已登录用户先进行检测用户状态是否异常
            String openid=((UserInfo)authentication.getDetails()).getOpenid();
            Map<String, Object> userMap = this.wechatUserService.getUser(openid);
            if(null !=userMap) {
                // 更新用户信息
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userMap.get("useraccount"), "");
                UserInfo userInfo = createUserInfo(userMap, request);
                authenticationToken.setDetails(userInfo);
                authentication = super.getAuthenticationManager().authenticate(authenticationToken);
                // 写到会话
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // 已登录用户-访问登录页面，直接跳转至首页
                if (request.getRequestURI().equals(request.getContextPath() + this.loginFormUrl)) {
                    this.successHandler.onAuthenticationSuccess(request, response, authentication);
                    return;
                }

                //否则执行验证
                InterceptorStatusToken token = super.beforeInvocation(filterInvocation);
                try {
                    filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
                } finally {
                    super.afterInvocation(token, null);
                }
            }else{
                //异常情况，跳转到登录页面重新登录
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                String jumpUrl = this.getTargetUrl(request,((UserInfo)authentication.getDetails()).getOpenid());
                //将会话信息置空
                SecurityContextHolder.getContext().setAuthentication(null);
                if (this.useForward) {
                    RequestDispatcher dispatcher = request.getRequestDispatcher(jumpUrl);
                    dispatcher.forward(request, response);
                } else {
                    this.redirectStrategy.sendRedirect(request, response, jumpUrl);
                }
            }
        }
        */
    }

    /**
     * 创建用户信息
     *
     * @param userMap
     * @param request
     * @return
     */
    private UserInfo createUserInfo(Map<String, Object> userMap, HttpServletRequest request) {
        UserInfo userInfo = new UserInfo(userMap.get("useraccount")+"", "");
        userInfo.setIpAddress(request.getRemoteAddr());
        userInfo.setLoginTime(new Date());
        userInfo.setPhone(userMap.get("phone")+"");
        userInfo.setIdentityId(userMap.get("identityId")+"");
        userInfo.setName(userMap.get("username")+"");
        userInfo.setAuthStatus(userMap.get("authStatus")+"");
        userInfo.setIdentityType(userMap.get("identityType")+"");
        userInfo.setIcon(userMap.get("icon")+"");
        userInfo.setOpenid(userMap.get("openid")+"");
        return userInfo;
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    public void setLoginFormUrl(String loginFormUrl) {
        this.loginFormUrl = loginFormUrl;
    }

    public void setUseForward(boolean useForward) {
        this.useForward = useForward;
    }

    public void setIngoredTargetUrls(List<String> ingoredTargetUrls) {
        this.ingoredTargetUrls = ingoredTargetUrls;
    }

    private String getTargetUrl(HttpServletRequest request) throws UnsupportedEncodingException {
        if (!this.isIgnored(request.getRequestURI().replaceFirst(request.getContextPath(), ""))) {
            String url = request.getRequestURL().toString() + (StringUtils.isNotBlank(request.getQueryString()) ? "?" + request.getQueryString() : "");
            return this.loginFormUrl + "?redirect=" + url;
        } else {
            return this.loginFormUrl;
        }
    }

    private String getTargetUrl(HttpServletRequest request,String openid) throws UnsupportedEncodingException {
        if (!this.isIgnored(request.getRequestURI().replaceFirst(request.getContextPath(), ""))) {
            String url = request.getRequestURL().toString() + (StringUtils.isNotBlank(request.getQueryString()) ? "?" + request.getQueryString() : "");
            //第一个openid供用户体系使用，第二个openid供回调后使用
            if(url.indexOf("openid=")<0){
                url=url+"&openid="+openid;
            }
            return this.loginFormUrl + "?resbind=szga&token="+this.userToken+"&openid="+openid+"&redirect=" + url;
        } else {
            return this.loginFormUrl;
        }
    }

    private String getTargetLoginUrl(HttpServletRequest request,String openid) throws UnsupportedEncodingException {
        if (!this.isIgnored(request.getRequestURI().replaceFirst(request.getContextPath(), ""))) {
            String url = request.getRequestURL().toString() + (StringUtils.isNotBlank(request.getQueryString()) ? "?" + request.getQueryString() : "");
            url=url+"&openid=" + openid;
            return this.loginFormUrl + "?redirect=" + url;
        } else {
            return this.loginFormUrl + "?openid=" + openid;
        }
    }

    private boolean isIgnored(String url) {
        for (String ingoredUrl : this.ingoredTargetUrls) {
            if (ingoredUrl.equals(url)) {
                return true;
            }
        }

        return false;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }
}
