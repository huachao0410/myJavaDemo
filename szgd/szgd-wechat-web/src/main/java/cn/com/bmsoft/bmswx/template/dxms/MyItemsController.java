package cn.com.bmsoft.bmswx.template.dxms;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.com.bmsoft.base.common.security.IContextService;
import cn.com.bmsoft.base.common.web.RequestUtil;
import cn.com.bmsoft.dxms.domain.Myitems;
import cn.com.bmsoft.dxms.service.face.IMyItemsService;
import cn.com.bmsoft.services.wechat.IWechatOauthService;
import cn.com.bmsoft.weixin.service.ICommonService;
import cn.com.bmsoft.weixin.service.IOauthService;



/**
 * 我的事项 控制器
 *
 * @作者 王盟涛
 */
@Controller("template.MyItemsController")
@RequestMapping("/weixin/myitems")
public class MyItemsController {

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
    
    @Value("${publicUserInfoService}")
    private String publicUserInfoService;//获取用户信息

    @Autowired
    private IOauthService oauthService;

    @Autowired
    private ICommonService commonService;

    @Autowired
    private IContextService contextService;

    @Autowired
    private IWechatOauthService wechatOauthService;

    @Autowired
    private IMyItemsService myItemsService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 我的所有事项
     *
     * @param request
     * @return
     */
    @RequestMapping("/index")
   public ModelAndView MyItems(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("template/dxms/myitems/index");
        try{
        	String openid = request.getParameter("openid");
        	System.out.println(openid);
        	ModelMap map = new ModelMap();
        	map.put("openid", openid);
            List<Myitems> list = this.myItemsService.page(0, 5, map);

            ObjectMapper ob = new ObjectMapper();
            mv.addObject("list", ob.writeValueAsString(list));
        }catch(Exception ex){
            ex.printStackTrace();
            mv.addObject("list", "[]");
        }
        return mv;
    }
    

    /**
     * 已办所有事项
     *
     * @param request
     * @return
     */
    @RequestMapping("/finisheditems")
    public ModelAndView FinishedItems(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("template/dxms/myitems/finisheditems");
       // Map<String, Object> queryParams = RequestUtil.asMap(request, false);
        String openid = request.getParameter("openid");
       // Boolean status = request.getParameter("status");
    	System.out.println(openid);
    	ModelMap map = new ModelMap();
    	map.put("openid", openid);
        List<Myitems> list = this.myItemsService.find(map);
        try{
            ObjectMapper ob = new ObjectMapper();
            mv.addObject("list", ob.writeValueAsString(list));
        }catch(Exception ex){
            mv.addObject("list", "[]");
        }

        return mv;
    }

    /**
     * 在办所有事项
     *
     * @param request
     * @return
     */
    @RequestMapping("/unfinisheditems")
    public ModelAndView UnfinishedItems(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("template/dxms/myitems/unfinisheditems");
        Map<String, Object> queryParams = RequestUtil.asMap(request, false);
        List<Myitems> list = this.myItemsService.find(queryParams);
        try{
            ObjectMapper ob = new ObjectMapper();
            mv.addObject("list", ob.writeValueAsString(list));
        }catch(Exception ex){
            mv.addObject("list", "[]");
        }
        return mv;
    }
    
    /**
     * 精确查询事项
     *
     * @param request
     * @return
     */
    @RequestMapping("/exactsearch")
	public ModelAndView Exactsearch(HttpServletRequest request){
    	ModelAndView mv = new ModelAndView("template/dxms/myitems/exactsearch");
    	String params = request.getParameter("params");
    	JSONArray json=JSONArray.parseArray(params);
    	JSONObject jsonOne;
    	ModelMap  map= new ModelMap();
    	//支持页面参数传递
    	if (json != null && json.size() > 0){
	 		for (int i=0;i<json.size();i++) {
	 			jsonOne = json.getJSONObject(i); 
		         map.put("bidid", (String) jsonOne.get("bidid"));
		         map.put("bidtime", (Date) jsonOne.get("bidtime"));
		         map.put("itemsname", (String) jsonOne.get("itemsname"));
	 		}
    	}
 		map.put("openid", (String) request.getParameter("openid"));
 		List<Myitems> list = this.myItemsService.find(map);
    	String openid = request.getParameter("openid");
    	System.out.println(openid);
    	try{
            ObjectMapper ob = new ObjectMapper();
            mv.addObject("list", ob.writeValueAsString(list));
        }catch(Exception ex){
            mv.addObject("list", "[]");
        }
        return mv;
	}
}
