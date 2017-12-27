package cn.com.bmsoft.bmswx.template.dxms;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.bmsoft.dxms.domain.Welfare;
import cn.com.bmsoft.dxms.service.face.IWelfareService;

/**
 * 我的情况 控制器
 *
 * @作者 刘辟锋
 */
@Controller("template.wdqkController")
@RequestMapping("/weixin/dxms")
public class WdqkController {

	@Autowired
	private IWelfareService welfareService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request){
		
		Welfare welfare = new Welfare();
		welfare.setId(5);
		welfare.setName("测试福利情况插入");
		//welfareService.insert(welfare);
		
		ModelAndView mv = new ModelAndView("template/dxms/home/index");
		return mv;
	}
	
	@RequestMapping("/wdqk")
	public ModelAndView wdqk(HttpServletRequest request){	
		
		ModelAndView mv = new ModelAndView("template/dxms/wdqk/index");
		return mv;
	}

}
