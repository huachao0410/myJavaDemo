package cn.com.bmsoft.base.common.security;

import java.io.Serializable;

import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录服务接口，用于验证登录
 *
 */
public interface ILoginService extends Serializable {

    /**
     * 登录验证服务，登录失败时抛出验证异常
     *
     * @param username
     * @param password
     * @throws AuthenticationException
     */
	UserInfo login(String username, String password, HttpServletRequest request) throws AuthenticationException;
}
