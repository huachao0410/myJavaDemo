package cn.com.bmsoft.weixin.service.impl;

import cn.com.bmsoft.weixin.service.IMaterialService;
import cn.com.bmsoft.weixin.util.HttpKit;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;
import com.alibaba.fastjson.JSONObject;

/**
 * 微信素材管理服务接口
 * Created by roly on 2017/7/10.
 */
@Service
public class MaterialServiceImpl implements IMaterialService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String MATERIAL_URI = "https://api.weixin.qq.com/cgi-bin/material";

	private static final String GET_MATERIAL_URI = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=";

	private static final String MEDIA_GET_URI = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=";

	/**
	 * 新增永久图文素材
	 *
	 * @param accessToken
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean CreateMaterial(String accessToken, String params) throws Exception {
		String jsonStr = HttpKit.post(MATERIAL_URI + "add_news?access_token=" + accessToken, params);
		Map<String, Object> map = JSON.parseObject(jsonStr, Map.class);
		boolean result = "0".equals(map.get("errcode").toString());
		logger.info("创建微信永久素材结果：" + map.get("errmsg"));
		return result;
	}

	/**
	 * 获取素材列表
	 *
	 * @param accessToken
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getMaterialList(String accessToken, String params) throws Exception {
		String jsonStr = HttpKit.post(MATERIAL_URI + "/batchget_material?access_token=" + accessToken, params);
		return jsonStr;

	}

	/**
	 * 获取素材
	 *
	 * @param accessToken
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> getMaterial(String accessToken, String params) throws Exception {
		String jsonStr = HttpKit.post(MATERIAL_URI + "/get_material?access_token=" + accessToken, params);
		Map<String, Object> map = JSON.parseObject(jsonStr, Map.class);
		return map;
	}

	/**
	 * 修改素材
	 *
	 * @param accessToken
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public String updateMaterial(String accessToken, String params) throws Exception {
		String jsonStr = HttpKit.get(MATERIAL_URI + "/update_news?access_token=" + accessToken);

		return jsonStr;
	}

	/**
	 * 删除素材
	 *
	 * @param accessToken
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public String delMaterial(String accessToken, String params) throws Exception {
		String jsonStr = HttpKit.get(MATERIAL_URI + "/del_material?access_token=" + accessToken);

		return jsonStr;
	}


	/**
	 * 新增永久素材，获取永久media_id
	 *
	 * @return
	 * @throws Exception
	 * @Override
	 */
	public JSONObject addMaterialEver(String fileurl, String type, String token) throws Exception {

		File file = new File(fileurl);
		//上传素材
		String path = GET_MATERIAL_URI.concat(token) + "&type=" + type;
		String result = null;
		result = HttpKit.uploadMaterial(path, file);
		result = result.replaceAll("[\\\\]", "");
		System.out.println("result:" + result);
		JSONObject resultJSON = JSONObject.parseObject(result);
		if (resultJSON != null) {
			if (resultJSON.get("thumb_media_id") != null) {
				//封面缩略图上传
				System.out.println("上传" + type + "永久素材成功");

			} else {
				System.out.println("上传" + type + "永久素材失败");
			}
			return resultJSON;
		}
		return null;
	}

	@Override
	public JSONObject getMaterialEver(String accessToken, String media_id) throws Exception {
		String jsonStr = HttpKit.get(MEDIA_GET_URI.concat(accessToken) + "&media_id=" + media_id);
		JSONObject resultJSON = JSONObject.parseObject(jsonStr);
		if (resultJSON != null) {
			return resultJSON;
		}
		return null;
	}

}
