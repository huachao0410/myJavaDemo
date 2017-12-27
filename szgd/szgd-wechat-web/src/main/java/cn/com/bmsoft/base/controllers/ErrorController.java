package cn.com.bmsoft.base.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 错误页面跳转控制器
 *
 */
@Controller
@RequestMapping(value = "/error")
public class ErrorController {

    @RequestMapping(value = "/{errorCode}")
    public ModelAndView error(@PathVariable int errorCode) {
    	ModelMap map = new ModelMap();
    	map.put("template", "template");
    	switch (errorCode) {
	        case 401:
	            return new ModelAndView("template/error/401",map);
	        case 403:
	            return new ModelAndView("template/error/403",map);
	        case 404:
	            return new ModelAndView("template/error/404",map);
	        case 500:
	            return new ModelAndView("template/error/500",map);
	        case 503:
	            return new ModelAndView("template/error/503",map);
	        default:
	            return new ModelAndView("template/error/404",map);
	    }
    }
}
