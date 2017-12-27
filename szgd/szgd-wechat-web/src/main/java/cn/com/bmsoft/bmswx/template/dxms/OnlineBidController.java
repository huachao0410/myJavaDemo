package cn.com.bmsoft.bmswx.template.dxms;

import java.util.HashMap;
import java.util.List;
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
import org.codehaus.jackson.map.ObjectMapper;
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
import cn.com.bmsoft.dxms.domain.OnlineBid;
import cn.com.bmsoft.dxms.service.face.IOnlineBidService;
import cn.com.bmsoft.weixin.service.IOauthService;

/**
 * 可在线申请 控制器
 *
 * @作者 XenogearTang
 */
@Controller("template.onlineBidController")
@RequestMapping("/weixin/onlinebid")
public class OnlineBidController {

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
	
	@Autowired
	private IOnlineBidService onlineBidService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("template/dxms/onlinebid/index");
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		List<OnlineBid> list = this.onlineBidService.find(queryParams);
		try{
			ObjectMapper ob = new ObjectMapper(); 
			mv.addObject("list", ob.writeValueAsString(list));
		}catch(Exception ex){
			mv.addObject("list", "[]");
		}
		return mv;
	}
	
	@RequestMapping("/index_")
	public ModelAndView index_(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("template/dxms/onlinebid/index_");
		return mv;
	}

}
