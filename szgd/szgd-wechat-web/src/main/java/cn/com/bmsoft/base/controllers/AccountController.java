package cn.com.bmsoft.base.controllers;

import cn.com.bmsoft.base.common.web.RequestUtil;
import cn.com.bmsoft.services.wechat.IWechatOauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping(value = "/account")
public class AccountController {

	@Value("${wx_appid}")
	private String appid;

	@Value("${wx_appsecret}")
	private String appsecret;

	@Value("${loginPath}")
	private String loginPath;

	@Autowired
	private IWechatOauthService wechatOauthService;

	/**
	 * 登录页面
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	public ModelAndView login(
			HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		ModelMap map = new ModelMap();
		//支持页面参数传递
		for (String key : queryParams.keySet()) {
			map.put(key, queryParams.get(key));
		}

		//js-api 校验接口参数
		map.put("appid", appid);

		//jsqpi签名
		map.put("signature", this.wechatOauthService.getJsapiSignature(request));

		return new ModelAndView(loginPath, map);
	}


}
