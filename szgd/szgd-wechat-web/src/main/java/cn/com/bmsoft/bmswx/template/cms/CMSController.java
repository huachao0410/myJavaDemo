package cn.com.bmsoft.bmswx.template.cms;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import cn.com.bmsoft.base.common.security.IContextService;
import cn.com.bmsoft.services.wechat.IWechatOauthService;
import cn.com.bmsoft.weixin.service.ICommonService;
import cn.com.bmsoft.weixin.util.HttpKit;
import cn.com.bmsoft.weixin.util.JsApiSign;
import cn.com.bmsoft.weixin.util.SHA1;
import cn.com.bmsoft.weixin.util.WxCardSign;
import com.alibaba.fastjson.JSONArray;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.alibaba.fastjson.JSONObject;

import cn.com.bmsoft.base.common.web.RequestUtil;
import cn.com.bmsoft.weixin.service.IOauthService;

/**
 * 门户模板 控制器
 *
 * @作者 容智达
 */
@Controller("template.cmsController")
@RequestMapping("/weixin/cms")
public class CMSController {

	@Value("${wx_appid}")
	private String appid;

	@Value("${wx_appsecret}")
	private String appsecret;

	@Value("${youtuUrl}")
	private String youtuUrl; //重定向地址

	@Value("${userToken}")
	private String userToken; //用户服务的token

	@Value("${userSessionService}")
	private String userSessionService; //用户会话服务地址

	@Value("${getPubliceUserForOpenid}")
	private String getPubliceUserForOpenid; //用户绑定openid的信息服务地址

	@Autowired
	private IOauthService oauthService;

	@Autowired
	private ICommonService commonService;

	@Autowired
	private IContextService contextService;

	@Autowired
	private IWechatOauthService wechatOauthService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 获取模板页面
	 *
	 * @param template
	 * @param path
	 * @param request
	 * @return
	 */
	@RequestMapping("/web")
	public ModelAndView webPage(
			@RequestParam(value = "template") String template,
			@RequestParam(value = "path") String path,
			HttpServletRequest request) {

		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		ModelMap map = new ModelMap();
		//支持页面参数传递
		for (String key : queryParams.keySet()) {
			map.put(key, queryParams.get(key));
		}
		map.put("template", template);
		map.put("appid", appid);

		Object code = queryParams.get("code");
		map.put("code", code);

		// 设置openid
		if (queryParams.get("openid") == null) {
			if (code != null) {
				map.put("openid", getUserOpenid(code.toString()));
			}
		} else {
			map.put("openid", queryParams.get("openid"));
		}

		// 用户信息，上下文环境获取
		map.put("user", contextService.getUserInfo());

		String accessToken = this.wechatOauthService.getAccessToken();

		//jsqpi签名
		map.put("signature", getSignature(request, accessToken));

		// 添加卡券的签名
		if (map.get("cardid") != null && map.get("openid") != null) {
			Map<String, String> addCardSignature = getAddCardSignature(map.get("cardid").toString(), map.get("openid").toString(), accessToken);
			map.put("addCardSignature", addCardSignature);
		}

		return new ModelAndView("template/cms/" + template + path, map);
	}

	/**
	 * 获取用户微信开发号
	 *
	 * @param code
	 * @return
	 */
	private String getUserOpenid(String code) {
		try {
			String token = this.oauthService.getToken(appid, appsecret, code);
			JSONObject obj = JSONObject.parseObject(token);
			Object openid = obj.get("openid");
			if (openid != null) {
				return openid + "";
			}
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * 获取jsapi签名
	 *
	 * @param request
	 * @return
	 */
	private Map<String, String> getSignature(HttpServletRequest request, String accessToken) {
		try {
			String url = request.getRequestURL().toString();
			Map<String, String> parameterMap = request.getParameterMap();
			if (parameterMap.keySet().size() > 0) {
				url += "?" + request.getQueryString();
			}

			String ticket = this.commonService.getTicket(accessToken);
			Map<String, String> signMap = this.commonService.sign(ticket, url);
			return signMap;
		} catch (Exception e) {
		}

		return null;
	}

	/**
	 * 获取卡券签名(添加到卡包)[api_ticket,timestamp,nonce_str,card_id,openid]
	 *
	 * @param cardId
	 * @param openid
	 * @return
	 */
	private Map<String, String> getAddCardSignature(String cardId, String openid, String accessToken) {
		try {
			Map<String, String> signMap = new HashMap<String, String>();
			WxCardSign cardSign = new WxCardSign();

			String timestamp = JsApiSign.create_timestamp();
			String nonceStr = JsApiSign.create_nonce_str();
			String ticket = this.commonService.getWxCardTicket(accessToken);
			cardSign.addData(ticket);
			cardSign.addData(timestamp);
			cardSign.addData(nonceStr);
			cardSign.addData(cardId);

			String signature = cardSign.getSignature();

//			cardSign.addData(openid);

			signMap.put("timestamp", timestamp);
			signMap.put("openid", openid);
			signMap.put("nonceStr", nonceStr);
			signMap.put("signature", signature);


			return signMap;
		} catch (Exception e) {
		}

		return null;
	}


	/**
	 * 拉起卡券列表
	 * @param accessToken
	 * @return
	 */
	private Map<String, String> getChooseCardSignature( String accessToken) {
		try {
			Map<String, String> signMap = new HashMap<String, String>();
			WxCardSign chooseCardSign = new WxCardSign();

			String timestamp = JsApiSign.create_timestamp();
			String nonceStr = JsApiSign.create_nonce_str();

			String ticket = this.commonService.getWxCardTicket(accessToken);

			chooseCardSign.addData("MEMBER_CARD");
//			chooseCardSign.addData("SHA1");
			chooseCardSign.addData(appid);
			chooseCardSign.addData(ticket);
			chooseCardSign.addData(timestamp);
			chooseCardSign.addData(nonceStr);

			String signature = chooseCardSign.getSignature();

			signMap.put("timestamp", timestamp);
			signMap.put("nonceStr", nonceStr);
			signMap.put("signature", signature);


			return signMap;
		} catch (Exception e) {
		}

		return null;
	}

	@RequestMapping("/redirect")
	public ModelAndView redirect(
			@RequestParam(value = "url") String url,
			HttpServletRequest request) {
		String newUrl = oauthService.getCode(appid, url);
		return new ModelAndView(new RedirectView(newUrl));
	}

	/**
	 * 设置ModelAndView重定向路径
	 *
	 * @param request
	 * @return
	 */
	private ModelAndView redirectModel(HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();
		url.append("?" + request.getQueryString());
		String newUrl = oauthService.getCode(appid, url.toString());
		return new ModelAndView(new RedirectView(newUrl));
	}

	/**
	 * 根据openid获取用户信息
	 *
	 * @param openid
	 * @return
	 */
	private Map<String, Object> getUserByOpenid(String openid) {
		try {
			String s = HttpKit.get(getPubliceUserForOpenid + openid + "?token=" + userToken);
			Map<String, Object> map = JSONObject.parseObject(s);
			if (map.get("code").equals("200")) {
				JSONArray ss = JSONObject.parseArray(map.get("datas").toString());
				if (ss.size() > 0) {
					Map<String, Object> jsonMap = JSONObject.parseObject(ss.getString(0));
					return jsonMap;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取模板页面(跳开登录)
	 *
	 * @param template
	 * @param path
	 * @param request
	 * @return
	 */
	@RequestMapping("/open")
	public ModelAndView openPage(
			@RequestParam(value = "template") String template,
			@RequestParam(value = "path") String path,
			HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		ModelMap map = new ModelMap();
		//支持页面参数传递
		for (String key : queryParams.keySet()) {
			map.put(key, queryParams.get(key));
		}
		map.put("template", template);
		map.put("appid", appid);


		// 美餐订餐系统签名
		if (queryParams.get("meican") != null) {
			String time = String.valueOf(System.currentTimeMillis());
			map.put("meicanSign", createMeicanSign(time));
			map.put("meicanSignTime", time);
		}
		return new ModelAndView("template/cms/" + template + path, map);
	}

	/**
	 * 美餐订餐系统签名
	 *
	 * @return
	 */
	private String createMeicanSign(String meicanTime) {
		// 微团参数
		String token = "32929925-a56b-4292-b501-e007f1b228c9";
//		String email = "liuhanliang@bmsoft.com.cn";
		String email = "thkjy@bmsoft.com.cn";
		return SHA1.encode(token + meicanTime + email);
	}


	/**
	 * 微信支付测试页面
	 *
	 * @param template
	 * @param path
	 * @param request
	 * @return
	 */
	@RequestMapping("/pay")
	public ModelAndView payPage(
			@RequestParam(value = "template") String template,
			@RequestParam(value = "path") String path,
			HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		ModelMap map = new ModelMap();
		//支持页面参数传递
		for (String key : queryParams.keySet()) {
			map.put(key, queryParams.get(key));
		}

		//js-api 校验接口参数
		map.put("template", template);
		map.put("appid", appid);

		String accessToken = this.wechatOauthService.getAccessToken();

		//jsqpi签名
		map.put("signature", getSignature(request, accessToken));

		// 添加卡券的签名
		if (map.get("cardid") != null && map.get("openid") != null) {
			Map<String, String> addCardSignature = getAddCardSignature(map.get("cardid").toString(), map.get("openid").toString(), accessToken);
			map.put("addCardSignature", addCardSignature);
		}

		//拉起卡券列表签名参数
		Map<String, String> chooseCardSignature = getChooseCardSignature(accessToken);
		map.put("chooseCardSignature",chooseCardSignature);

		return new ModelAndView("template/cms/" + template + path, map);
	}

}
