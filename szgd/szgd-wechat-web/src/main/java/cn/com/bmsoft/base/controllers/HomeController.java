package cn.com.bmsoft.base.controllers;

import javax.servlet.http.HttpServletRequest;

import cn.com.bmsoft.weixin.util.SHA1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.bmsoft.base.common.security.IContextService;

import java.security.MessageDigest;

@Controller
public class HomeController {
	
	@Autowired
	private IContextService contextService;

    @RequestMapping({"/","/index"})
    public ModelAndView index(HttpServletRequest request) {
    	ModelMap map = new ModelMap();
    	map.put("user", contextService.getUserInfo());
		map.put("useraccount", contextService.getUsername());
        return new ModelAndView("views/home/index", map);
    }
    
    @RequestMapping("/services")
    public ModelAndView services(HttpServletRequest request) {
    	ModelMap map = new ModelMap();
    	map.put("user", contextService.getUserInfo());
		map.put("useraccount", contextService.getUsername());
        return new ModelAndView("views/services/index", map);
    }
    
    @RequestMapping("/user")
    public ModelAndView user(HttpServletRequest request) {
    	ModelMap map = new ModelMap();
		map.put("user", contextService.getUserInfo());
		map.put("useraccount", contextService.getUsername());
        return new ModelAndView("template/cms/default/usercenter/usercenter", map);
    }
}
