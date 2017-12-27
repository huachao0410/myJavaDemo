package cn.com.bmsoft.bmswx.template.cms;

import cn.com.bmsoft.base.common.response.ResponseBean;
import cn.com.bmsoft.base.common.response.ResponseCode;
import cn.com.bmsoft.base.common.security.IContextService;
import cn.com.bmsoft.base.common.service.face.IBmsEsbService;
import cn.com.bmsoft.base.common.web.RequestUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 北明服务总线提供的服务控制器
 */
@Controller
@RequestMapping(value = "/weixin/bmsesb")
public class BmsEsbServiceController {

	@Autowired
	private IContextService contextService;

	@Autowired
	private IBmsEsbService bmsEsbService;

	@ResponseBody
	@RequestMapping("/get")
	public ResponseBean<Object> get(
			@RequestParam("cmd") String cmd,
			HttpServletRequest request) {
		ResponseBean<Object> bean = new ResponseBean<Object>();
		try {
			Map<String, Object> queryParams = RequestUtil.asMap(request, false);
			queryParams.remove("cmd");
			queryParams.put("username",contextService.getUsername());
			return bmsEsbService.doGet(cmd, queryParams);
		} catch (Exception e) {
			bean.setCode(ResponseCode.FAILED_500);
		}
		return bean;
	}


	@ResponseBody
	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public ResponseBean<Object> post(
			@RequestParam("cmd") String cmd,
			HttpServletRequest request) {
		ResponseBean<Object> bean = new ResponseBean<Object>();
		try {
			Map<String, Object> queryParams = RequestUtil.asMap(request, false);
			queryParams.remove("cmd");
			queryParams.put("username",contextService.getUsername());
			return bmsEsbService.doPost(cmd, queryParams);
		} catch (Exception e) {
			bean.setCode(ResponseCode.FAILED_500);
		}
		return bean;
	}

}
