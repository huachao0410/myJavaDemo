package cn.com.bmsoft.base.common.security.sso;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

//import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.com.bmsoft.base.common.security.IContextService;
import cn.com.bmsoft.base.common.security.SecurityFilter;
import cn.com.bmsoft.base.common.security.UserInfo;

/**
 * 单点登录拦截器
 *
 * @author daniel
 */
public class CasSecurityFilter extends SecurityFilter {

    @Autowired
    private IContextService contextService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        FilterInvocation filterInvocation = new FilterInvocation(request, response, chain);
        this.invoke(filterInvocation);
    }

    private void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {
        SecurityContextHolderAwareRequestWrapper wrapper = (SecurityContextHolderAwareRequestWrapper) filterInvocation.getRequest();
        HttpServletRequest request = (HttpServletRequest) wrapper.getRequest();

        //
        ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(requestAttributes);

        //保存单点登录信息
//      String username = this.contextService.getUsername();
        String useraccount = null;
        Map<String, Object> attributes = null;
        try {
//            AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
//            attributes = principal.getAttributes();//返回用户信息
//            useraccount = attributes.get("useraccount").toString();//用户姓名
        } catch (Exception e) {
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean nameChnge = authentication == null || !(useraccount.equals(authentication.getPrincipal().toString()));
        if (nameChnge) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(useraccount, "");
            UserInfo userInfo = new UserInfo(useraccount, "");
            userInfo.setIpAddress(request.getRemoteAddr());
            userInfo.setLoginTime(new Date());
            String phone = attributes.get("phone").toString();//用户手机号码
            String identityId = attributes.get("identityId").toString();//用户身份证号码
            String username = attributes.get("username").toString();//用户姓名
            String authStatus = attributes.get("authStatus")==null?"" : attributes.get("authStatus").toString();//实名认证状态
            userInfo.setPhone(phone);
            userInfo.setIdentityId(identityId);
            userInfo.setName(username);
            userInfo.setAuthStatus(authStatus);
            token.setDetails(userInfo);

            authentication = this.getAuthenticationManager().authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        //验证权限
        InterceptorStatusToken token = super.beforeInvocation(filterInvocation);
        try {
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }
}
