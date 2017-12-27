package cn.com.bmsoft.bmswx.template.dxms;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.bmsoft.base.common.security.IContextService;
import cn.com.bmsoft.services.wechat.IWechatOauthService;
import cn.com.bmsoft.weixin.service.ICommonService;
import cn.com.bmsoft.weixin.service.IOauthService;

/**
 * 服务分类 控制器
 *
 * @作者 wuli
 */
@Controller("template.fwflController")
@RequestMapping("/weixin/fwfl")
public class FwflController {

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
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("template/dxms/fwfl/index");
		return mv;
	}
	
	/**
	 * 困难家庭
	 * @param request
	 * @return
	 */
	@RequestMapping("/knjt")
	public ModelAndView knjt(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("template/dxms/fwfl/knjt");
		return mv;
	}
	
	/**
	 * 毕业生
	 * @param request
	 * @return
	 */
	@RequestMapping("/bys")
	public ModelAndView bys(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("template/dxms/fwfl/bys");
		return mv;
	}
	/**
	 * 残疾人
	 * @param request
	 * @return
	 */
	@RequestMapping("/cjr")
	public ModelAndView cjr(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("template/dxms/fwfl/cjr");
		return mv;
	}
	/**
	 * 低保家庭
	 * @param request
	 * @return
	 */
	@RequestMapping("/dbjt")
	public ModelAndView dbjt(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("template/dxms/fwfl/dbjt");
		return mv;
	}
	/**
	 * 军人
	 * @param request
	 * @return
	 */
	@RequestMapping("/jr")
	public ModelAndView jr(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("template/dxms/fwfl/jr");
		return mv;
	}
	/**
	 * 失业人员
	 * @param request
	 * @return
	 */
	@RequestMapping("/syry")
	public ModelAndView syry(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("template/dxms/fwfl/syry");
		return mv;
	}
	/**
	 * 再就业人员
	 * @param request
	 * @return
	 */
	@RequestMapping("/zjyry")
	public ModelAndView zjyry(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("template/dxms/fwfl/zjyry");
		return mv;
	}
	/**
	 * 受灾人员
	 * @param request
	 * @return
	 */
	@RequestMapping("/szry")
	public ModelAndView szry(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("template/dxms/fwfl/szry");
		return mv;
	}

}
