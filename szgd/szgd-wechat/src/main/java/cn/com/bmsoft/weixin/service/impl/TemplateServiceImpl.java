package cn.com.bmsoft.weixin.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.com.bmsoft.weixin.bean.TemplateData;
import cn.com.bmsoft.weixin.bean.TemplateModelList;
import cn.com.bmsoft.weixin.service.ITemplateService;
import cn.com.bmsoft.weixin.util.HttpKit;

import com.alibaba.fastjson.JSONObject;

@Service
public class TemplateServiceImpl implements ITemplateService{
	
	private final static String SET_INDUSTRY_URI="https://api.weixin.qq.com/cgi-bin/template/api_set_industry";
	private final static String GET_INDUSTRY_URI="https://api.weixin.qq.com/cgi-bin/template/get_industry";
	private final static String GET_TEMPLATE_ID_URI="https://api.weixin.qq.com/cgi-bin/template/api_add_template";
	private final static String GET_TEMPLATE_LIST_URI="https://api.weixin.qq.com/cgi-bin/template/get_all_private_template";
	private final static String DELETE_TEMPLATE_URI="https://api.weixin.qq.com/cgi-bin/template/del_private_template";
	private final static String SEND_TEMPLATE_URI="https://api.weixin.qq.com/cgi-bin/message/template/send";
	
	
	@Override
	public boolean setIndustry(String accessToken, String industryId1,
			String industryId2) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessToken);
		params.put("industry_id1", industryId1);
		params.put("industry_id2", industryId2);
		String  jsonStr = HttpKit.post(SET_INDUSTRY_URI, params);
		if(StringUtils.isNotEmpty(jsonStr)){
			JSONObject obj = JSONObject.parseObject(jsonStr);
			if(null != obj.get("errmsg")){
				//TODO
			}
		}
		return true;
	}

	@Override
	public JSONObject getIndustry(String accessToken) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessToken);
		String  jsonStr = HttpKit.get(GET_INDUSTRY_URI, params);
		if(StringUtils.isNotEmpty(jsonStr)){
			JSONObject obj = JSONObject.parseObject(jsonStr);
			return obj;
		}
		return null;
	}

	@Override
	public String getTemplateId(String accessToken, String templateIdShort)
			throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessToken);
		params.put("template_id_short", templateIdShort);
		String  jsonStr = HttpKit.post(GET_TEMPLATE_ID_URI, params);
		if(StringUtils.isNotEmpty(jsonStr)){
			JSONObject obj = JSONObject.parseObject(jsonStr);
			if(null != obj.get("errmsg") && obj.get("errmsg").equals("ok")){
				return (String) (obj.get("template_id") == null?"":obj.get("template_id"));
			}
		}
		return "";
	}

	@Override
	public TemplateModelList getTemplateList(String accessToken)
			throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessToken);
		String  jsonStr = HttpKit.get(GET_TEMPLATE_LIST_URI, params);
		if(StringUtils.isNotEmpty(jsonStr)){
			TemplateModelList list=JSONObject.parseObject(jsonStr, TemplateModelList.class);	
			return list;
		}
		return null;
	}

	@Override
	public boolean deleteTemplateById(String accessToken, String templateId)
			throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessToken);
		params.put("template_id", templateId);
		String  jsonStr = HttpKit.post(DELETE_TEMPLATE_URI, params);
		if(StringUtils.isNotEmpty(jsonStr)){
			JSONObject obj = JSONObject.parseObject(jsonStr);
			if(null != obj.get("errmsg") && obj.get("errmsg").equals("ok")){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean send(String accessToken, TemplateData data) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessToken);
		params.put("touser", data.getTouser());
		params.put("template_id", data.getTemplate_id());
		params.put("url", data.getUrl());
		params.put("miniprogram", JSONObject.toJSONString(data.getMiniprogram()));
		params.put("data", JSONObject.toJSONString(data.getData()));
		String  jsonStr = HttpKit.post(SEND_TEMPLATE_URI, params);
		if(StringUtils.isNotEmpty(jsonStr)){
			JSONObject obj = JSONObject.parseObject(jsonStr);
			if(null != obj.get("errmsg") && obj.get("errmsg").equals("ok")){
				return true;
			}
		}
		return false;
	}

}
