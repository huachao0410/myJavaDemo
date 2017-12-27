package cn.com.bmsoft.bmswx.template.cms;
import cn.com.bmsoft.base.common.response.ResponseBean;
import cn.com.bmsoft.base.common.response.ResponseCode;
import cn.com.bmsoft.weixin.service.ICommonService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信授权
 * Created by gzh on 2017/8/16.
 */
@Controller("weixin.oauth")
@RequestMapping("/weixin/oauth")
public class AuthorizationWxController {

	@Value("${wx_appid}")
	private String appid;

	@Value("${wx_appsecret}")
	private String appsecret;
	@Autowired
	private ICommonService commonService;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/authorization", method = RequestMethod.GET)
	public ResponseBean<Map<String,Object>> authorizationWeixin(
			@RequestParam(value = "code") String code, HttpServletRequest request ) {
		ResponseBean bean = new ResponseBean();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String result = this.commonService.getOauth2Token(appid,appsecret,code);
			JSONObject jsonObject = JSON.parseObject(result);
			if (jsonObject!=null && jsonObject.get("errcode") !=null ) {

				bean.setCode(ResponseCode.FAILED_400);
				bean.setErrorMessage(result);
			}else if (jsonObject!=null && jsonObject.get("openid") !=null){

				String sex = jsonObject.getString("sex");
				String city = jsonObject.getString("city");
				String openid = jsonObject.getString("openid");
				String country = jsonObject.getString("country");
				String nickname = jsonObject.getString("nickname");
				String language = jsonObject.getString("language");
				String province = jsonObject.getString("province");
				String privilege = jsonObject.getString("privilege");
				String headimgurl = jsonObject.getString("headimgurl");

				map.put("sex",sex);
				map.put("city",city);
				map.put("openid",openid);
				map.put("country",country);
				map.put("nickname",nickname);
				map.put("language",language);
				map.put("province",province);
				map.put("privilege",privilege);
				map.put("headimgurl",headimgurl);

				bean.getDatas().add(map);
			}else {
				bean.setCode(ResponseCode.FAILED_400);
				bean.setErrorMessage("获取到jsonobject值为空");
			}

		} catch (Exception e) {
			logger.info(e.getMessage());
			bean.setCode(ResponseCode.FAILED_500);
		}
		return bean;
	}
}
