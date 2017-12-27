package cn.com.bmsoft.base.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Spring触发的登录验证拦截器
 */
@Service
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private ILoginService loginService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = this.obtainUsername(request).trim();
        String password = this.obtainPassword(request).trim();

        try {
        	UserInfo userInfo = this.loginService.login(username, password, request);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            		userInfo.getUsername(), password);
            userInfo.setIpAddress(request.getRemoteAddr());
            userInfo.setLoginTime(new Date());
            authenticationToken.setDetails(userInfo);
            Authentication authentication = super.getAuthenticationManager().authenticate(authenticationToken);
            return authentication;
        } catch (AuthenticationException e) {
            throw e;
        }
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter("username");
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter("password");
    }
}
