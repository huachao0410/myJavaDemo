package cn.com.bmsoft.mobile;

import cn.com.bmsoft.base.common.response.ResponseBean;
import cn.com.bmsoft.base.common.response.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.com.bmsoft.weixin.bean.UserInfo;
import cn.com.bmsoft.weixin.service.IOauthService;
import cn.com.bmsoft.weixin.service.IUserService;

/**
 * 移动公共服务控制器
 * 
 * @author daniel
 * 
 */
@Controller("mobile.mobileCommonController")
@RequestMapping("/mobile/common")
public class MobileCommonController {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${wx_appid}")
	private String appid;
	
	@Value("${wx_appsecret}")
	private String appsecret;

	@Autowired
	private IOauthService oauthService;
	
	@Autowired
	private IUserService userService;

	/**
	 * 获取微信开发号
	 *
	 * @param code 微信返回码
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/openid")
	public ResponseBean<String> getOpenId(@RequestParam("code") String code) {

		ResponseBean<String> bean = new ResponseBean<String>();

		String token;
		try {
			token = this.oauthService.getToken(appid, appsecret, code);
		} catch (Exception e) {
			bean.setCode(ResponseCode.FAILED_500);
			return bean;
		}
		JSONObject obj = JSONObject.parseObject(token);
		Object openid = obj.get("openid");
		if(openid != null) {
			bean.getDatas().add(openid + "");
		}
		return bean;
	}
	
	/**
	 * 获取微信账户信息
	 *
	 * @param openid 微信开放号
	 * @param code 微信返回码
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/userinfo")
	public ResponseBean<UserInfo> getUserInfo(@RequestParam("code") String code, @RequestParam("openid") String openid) {

		ResponseBean<UserInfo> bean = new ResponseBean<UserInfo>();

		String token;
		try {
			token = this.oauthService.getToken(appid, appsecret, code);
			JSONObject obj = JSONObject.parseObject(token);
			
			Object accessToken = obj.get("access_token");
			if(accessToken != null && accessToken.toString().length() > 0) {
				UserInfo userInfo = this.userService.getUserInfo(accessToken.toString(), openid);
				bean.getDatas().add(userInfo);
			} 
		} catch (Exception e) {
			logger.error("获取微信账户信息。", e);
			bean.setCode(ResponseCode.FAILED_500);
			return bean;
		}

		return bean;
	}
	
	

}
