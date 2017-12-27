package cn.com.bmsoft.base.security;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cn.com.bmsoft.base.common.security.ILoginService;
import cn.com.bmsoft.base.common.security.UserInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录验证实现类
 *
 */
@Service
public class LoginService implements ILoginService {

    private static final long serialVersionUID = 1L;

    /**
     * 登录验证，验证无效登录时候抛出AuthenticationException异常
     *
     * @see UsernameNotFoundException
     * @see DisabledException
     * @see AuthenticationException
     *
     * @param username
     * @param password
     * @throws AuthenticationException
     */
    @Override
    public UserInfo login(String username, String password, HttpServletRequest request) throws AuthenticationException {
        //TODO:自定义验证规则
    	return new UserInfo(username, password);
    }
}
