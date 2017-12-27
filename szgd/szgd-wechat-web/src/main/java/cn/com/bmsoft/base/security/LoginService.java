package cn.com.bmsoft.base.security;

import cn.com.bmsoft.base.common.security.ILoginService;
import cn.com.bmsoft.base.common.security.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录验证实现类
 */
@Service
public class LoginService implements ILoginService {
	
	@Value("${publicUserLoginService}")
	private String publicUserLoginService;
	
	@Value("${publicUserInfoService}")
	private String publicUserInfoService;

    private static final long serialVersionUID = 1L;

    /**
     * 登录验证，验证无效登录时候抛出AuthenticationException异常
     */
    @Override
    public UserInfo login(String username, String password, HttpServletRequest request) throws AuthenticationException {
		UserInfo userInfo = new UserInfo(username, password);
		userInfo.setName(username);

		return userInfo;
		/*
		if(admins.isEmpty()) {
			throw new UsernameNotFoundException("账户不存在");
		} else if(admins.size() != 1) {
			throw new DisabledException("账户无效或异常，请联系管理员。");
		} else if (password == null || !StringUtil.md5Pwd(password).equals(admins.get(0).getPassword())) {
                throw new AuthenticationException("密码验证失败。"){};
		}
		*/
    }

}
