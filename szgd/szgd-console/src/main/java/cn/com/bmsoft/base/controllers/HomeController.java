package cn.com.bmsoft.base.controllers;

import cn.com.bmsoft.base.common.security.IContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


@Controller
public class HomeController {

	@Autowired
	private IContextService contextService;

	@RequestMapping(value = { "/home/index", "/home", "" }, method = RequestMethod.GET)
	public ModelAndView index(Locale locale, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		ModelAndView view = new ModelAndView("views/home/index", map);
		return view;
	}
}