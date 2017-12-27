package cn.com.bmsoft.services.user;

import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * 用户服务
 */
public interface IWechatUserService {
	
	//判断是否已登录
	public boolean checkLogin(String openid);
	
	//获取用户信息
	public Map<String,Object> getUser(String openid);
	
}
