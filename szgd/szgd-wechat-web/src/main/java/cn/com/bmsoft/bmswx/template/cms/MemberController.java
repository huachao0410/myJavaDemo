package cn.com.bmsoft.bmswx.template.cms;

import cn.com.bmsoft.base.common.web.RequestUtil;
import cn.com.bmsoft.weixin.service.IOauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 会员控制器（用户中心）
 */
@Controller
@RequestMapping(value = "/weixin/member")
public class MemberController {
	
	@Value("${wx_appid}")
	private String appid;
	
	@Value("${wx_appsecret}")
	private String appsecret;
	
	@Autowired
	private IOauthService oauthService;
	
	/**
	 * 发送微信模版消息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/usercener")
	public ModelAndView userCener(HttpServletRequest request){
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		ModelMap map = new ModelMap();
		return new ModelAndView("template/cms/default/usercenter/usercenter", map);
	}
}
