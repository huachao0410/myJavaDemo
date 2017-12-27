package cn.com.bmsoft.weixin.service;

import cn.com.bmsoft.weixin.bean.TemplateData;
import cn.com.bmsoft.weixin.bean.TemplateModelList;

import com.alibaba.fastjson.JSONObject;


/**
 * 微信推送模版
 * @author czy
 *
 */
public interface ITemplateService {

	/**
	 * 设置所属行业信息
	 * @param accessToken
	 * @param industryId1
	 * @param industryId2
	 * @return
	 * @throws Exception
	 */
	public boolean setIndustry(String accessToken,String industryId1,String industryId2) throws Exception;
	
	/**
	 * 获取所属行业信息
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public JSONObject getIndustry(String accessToken) throws Exception;
	
	/**
	 * 获取模版ID
	 * @param accessToken
	 * @param templateIdShort
	 * @return 
	 * @throws Exception
	 */
	public String getTemplateId(String accessToken,String templateIdShort) throws Exception;
	
	
	/**
	 * 获取模版列表
	 * @param accessToken
	 * @return 
	 * @throws Exception
	 */
	public TemplateModelList getTemplateList(String accessToken) throws Exception;
	
	/**
	 * 删除模版
	 * @param accessToken
	 * @param templateId
	 * @return
	 * @throws Exception
	 */
	public boolean deleteTemplateById(String accessToken,String templateId) throws Exception;
	
	/**
	 * 发送模版
	 * @param accessToken
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public boolean send(String accessToken,TemplateData data) throws Exception;
}
