package cn.com.bmsoft.bmswx.template.cms;

import javax.servlet.http.HttpServletRequest;

import cn.com.bmsoft.base.common.response.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.bmsoft.weixin.bean.TemplateData;
import cn.com.bmsoft.weixin.bean.TemplateData.TemplateDataItem;
import cn.com.bmsoft.weixin.service.IOauthService;
import cn.com.bmsoft.weixin.service.ITemplateService;

@Controller
@RequestMapping(value = "/weixin/component")
public class ComponentController {
	private final static String TEMPLATE_ID="F2ADW0AWYH0BO7Z7Q1OqdgV3ObKBdri6gQHDH5158C4";
	
	@Value("${wx_appid}")
	private String appid;
	
	@Value("${wx_appsecret}")
	private String appsecret;
	
	@Autowired
	private IOauthService oauthService;
	
	@Autowired
	private ITemplateService templateService;
	
	/**
	 * 发送微信模版消息
	 * @param code
	 * @param openid
	 * @param firstData
	 * @param key1Data
	 * @param key2Data
	 * @param remarkData
	 * @param redirectUrl
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sendTemplate")
	public ResponseBean sendTemplate(
			@RequestParam("code") String code,
			@RequestParam("openid") String openid,
			@RequestParam("firstData") String firstData,
			@RequestParam("key1Data") String key1Data,
			@RequestParam("key2Data") String key2Data,
			@RequestParam("remarkData") String remarkData,
			@RequestParam("redirectUrl") String redirectUrl,
			HttpServletRequest request){
		ResponseBean result=new ResponseBean();
		try {
			//获取access_token
			String accessToken=this.oauthService.getToken(appid, appsecret, code);
			//创建模版
			TemplateData templateData=new TemplateData(openid, TEMPLATE_ID, redirectUrl);
			
			//设置模版内容
			TemplateDataItem templateDataItem=templateData.getData();
			templateDataItem.addItem("first", firstData);
			templateDataItem.addItem("keyword1", key1Data);
			templateDataItem.addItem("keyword2", key2Data);
			templateDataItem.addItem("remark", remarkData);
			templateData.setData(templateDataItem);
			//发送模版
			if(!this.templateService.send(accessToken, templateData)){
				result.setCode("401");
				result.setErrorMessage("发送微信模版失败");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode("404");
			result.setErrorMessage("获取access_token失败");
		}
		
		return result;
	}
}
