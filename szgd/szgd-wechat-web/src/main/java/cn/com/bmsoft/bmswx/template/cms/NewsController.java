package cn.com.bmsoft.bmswx.template.cms;

import cn.com.bmsoft.base.common.web.RequestUtil;
import cn.com.bmsoft.weixin.service.IOauthService;
import org.apache.commons.lang.StringUtils;
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
 * 新闻类控制器
 */
@Controller
@RequestMapping(value = "/weixin/news")
public class NewsController {

	@ResponseBody
	@RequestMapping("/channel")
	public ModelAndView channel(
			@RequestParam("channelId") Integer channelId,
			@RequestParam("template") String template,
			@RequestParam("model") String model,
			HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		ModelMap map = new ModelMap();
		map.put("channelId", channelId);
		map.putAll(queryParams);

		if(!StringUtils.isBlank(model)) {
			model = "_" + model;
		}
		return new ModelAndView("template/cms/" + template + "/channel" + model, map);
	}


	@ResponseBody
	@RequestMapping("/content")
	public ModelAndView content(
			@RequestParam("contentId") Integer contentId,
			@RequestParam("template") String template,
			@RequestParam("model") String model,
			HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		ModelMap map = new ModelMap();
		map.put("contentId", contentId);
		map.putAll(queryParams);

		if(!StringUtils.isBlank(model)) {
			model = "_" + model;
		}
		return new ModelAndView("template/cms/" + template + "/content" + model, map);
	}

}
