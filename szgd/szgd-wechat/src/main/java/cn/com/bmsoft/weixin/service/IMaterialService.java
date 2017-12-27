package cn.com.bmsoft.weixin.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * Created by gzh on 2017/7/10.
 */
public interface IMaterialService {

	/**
	 * 新增永久图文素材
	 *
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public boolean CreateMaterial(String accessToken, String params) throws Exception;

	/**
	 * 获取素材列表
	 *
	 * @param accessToken
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getMaterialList(String accessToken, String params) throws Exception;


	/**
	 * 获取永久素材
	 *
	 * @param accessToken
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getMaterial(String accessToken, String params) throws Exception;


	/**
	 * 修改永久素材
	 *
	 * @param accessToken
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String updateMaterial(String accessToken, String params) throws Exception;


	/**
	 * 删除永久素材
	 *
	 * @param accessToken
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String delMaterial(String accessToken, String params) throws Exception;

	/**
	 * 新增永久素材，获取永久media_id
	 * @param fileurl
	 * @param type
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public JSONObject addMaterialEver(String fileurl, String type, String token) throws Exception;


	/**
	 * 获取临时素材
	 * @param token
	 * @param media_id
	 * @return
	 * @throws Exception
	 */
	public JSONObject getMaterialEver(String token,String media_id) throws Exception;
}
