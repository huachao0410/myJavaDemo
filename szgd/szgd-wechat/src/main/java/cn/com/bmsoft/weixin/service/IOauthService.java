package cn.com.bmsoft.weixin.service;


/**
 * 微信Oauth和支付工具服务
 * 
 * @author daniel
 *
 */
public interface IOauthService {
	
	public String getCode(String appid, String redirectUri);
	
	public String getToken(String appid,String secret,String code) throws Exception;
	
	public String getRefreshToken(String appid,String refreshToken) throws Exception;

}
