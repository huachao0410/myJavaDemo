package cn.com.bmsoft.weixin.service.impl;

import cn.com.bmsoft.weixin.bean.Tag;
import cn.com.bmsoft.weixin.bean.Tags;
import cn.com.bmsoft.weixin.service.ITagService;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import cn.com.bmsoft.weixin.util.HttpKit;
import org.springframework.stereotype.Service;


/**
 * Created by gzh on 2017/7/27.
 */
@Service
public class TagServiceImpl implements ITagService{

	/**
	 * 1.创建标签
	 */
	private static final String TAG_CREATE_URI = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token=";
	/**
	 * 2.获取公众号已创建的标签
	 */
	private static final String TAGS_GET_URI = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=";
	/**
	 * 3.编辑标签
	 */
	private static final String TAG_UPDATE_URI = "https://api.weixin.qq.com/cgi-bin/tags/update?access_token=";
	/**
	 * 4.删除标签
	 */
	private static final String TAG_DELETE_URI = "https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=";
	/**
	 * 5. 获取标签下粉丝列表
	 */
	private static final String TAG_GET_URI = "https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=";
	/**
	 * 6. 批量为用户打标签
	 */
	private static final String BATCH_TAGGING = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=";
	/**
	 * 7.批量为用户取消标签
	 */
	private static final String BATCH_UNTAGGING = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=";
	/**
	 * 8.获取用户身上的标签列表
	 */
	private static final String TAG_GETID_URI = "https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token=";



	/**
	 * 1.创建标签
	 *
	 * @param accessToken
	 * @param name        标签名（30个字符以内
	 * @return
	 * @throws Exception
	 */
	@Override
	public Tag create(String accessToken, String name) throws Exception {
		Map<String, Object> groupMap = new HashMap<String, Object>();
		Map<String, Object> nameObj = new HashMap<String, Object>();
		nameObj.put("name", name);
		groupMap.put("tag", nameObj);
		String post = JSONObject.toJSONString(groupMap);
		String jsonStr = HttpKit.post(TAG_CREATE_URI.concat(accessToken), post);
		if (StringUtils.isNotEmpty(jsonStr)) {
			JSONObject obj = JSONObject.parseObject(jsonStr);

			if (obj.get("errcode") != null) {
				throw new Exception(obj.getString("errmsg"));
			}
			Tag tag = JSONObject.toJavaObject(obj, Tag.class);
			return tag;
		}
		return null;
	}

	/**
	 * 2.获取公众号已创建的标签
	 *
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	@Override
	public Tags get(String accessToken) throws Exception {
		String jsonStr = HttpKit.get(TAGS_GET_URI.concat(accessToken));
		if (StringUtils.isNotEmpty(jsonStr)) {
			JSONObject obj = JSONObject.parseObject(jsonStr);
			if (obj.get("errcode") != null) {
				throw new Exception(obj.getString("errmsg"));
			}
			Tags tags = JSONObject.toJavaObject(obj, Tags.class);
			return tags;
		}
		return null;
	}

	/**
	 * 3.编辑标签
	 *
	 * @param accessToken
	 * @param id          分组id，由微信分配
	 * @param name        分组名字（30个字符以内）
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject updateTag(String accessToken, String id, String name) throws Exception {
		Map<String, Object> group = new HashMap<String, Object>();
		Map<String, Object> nameObj = new HashMap<String, Object>();
		nameObj.put("name", name);
		nameObj.put("id", id);
		group.put("tag", nameObj);
		String post = JSONObject.toJSONString(group);
		String reslut = HttpKit.post(TAG_UPDATE_URI.concat(accessToken), post);
		if (StringUtils.isNotEmpty(reslut)) {
			return JSONObject.parseObject(reslut);
		}
		return null;
	}


	/**
	 * 4. 删除标签
	 *
	 * @param accessToken
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject delete(String accessToken, int id) throws Exception {
		String strJson = "{\"tag\":{\"id\":" + id + "}}";
		String reslut = HttpKit.post(TAG_DELETE_URI.concat(accessToken), strJson);
		if (StringUtils.isNotEmpty(reslut)) {
			return JSONObject.parseObject(reslut);
		}
		return null;
	}

	/**
	 * 5. 获取标签下粉丝列表
	 *
	 * @param tagid
	 * @param next_openid
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject tagGetOpenids(String accessToken, int tagid, String next_openid) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tagid", tagid);
		map.put("next_openid", next_openid);
		String post = JSONObject.toJSONString(map);
		String jsonStr = HttpKit.post(TAG_GET_URI.concat(accessToken), post);
		if (StringUtils.isNotEmpty(jsonStr)) {
			JSONObject obj = JSONObject.parseObject(jsonStr);
			return obj;
		}
		return null;
	}

	/**
	 * 6. 批量为用户打标签
	 *
	 * @param openid_list
	 * @param tagid
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject batchTagging(String accessToken, List<String> openid_list, int tagid) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("openid_list", openid_list);
		map.put("tagid", tagid);
		String post = JSONObject.toJSONString(map);
		String jsonStr = HttpKit.post(BATCH_TAGGING.concat(accessToken), post);
		if (StringUtils.isNotEmpty(jsonStr)) {
			JSONObject obj = JSONObject.parseObject(jsonStr);
			return obj;
		}
		return null;
	}

	/**
	 * 7.批量为用户取消标签
	 *
	 * @param openid_list
	 * @param tagid
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject batchuntagging(String accessToken, List<String> openid_list, int tagid) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("openid_list", openid_list);
		map.put("tagid", tagid);
		String post = JSONObject.toJSONString(map);
		String jsonStr = HttpKit.post(BATCH_UNTAGGING.concat(accessToken), post);
		if (StringUtils.isNotEmpty(jsonStr)) {
			JSONObject obj = JSONObject.parseObject(jsonStr);
			return obj;
		}
		return null;
	}

	/**
	 * 8. 获取用户身上的标签列表
	 *
	 * @param accessToken
	 * @param openid
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject getIdList(String accessToken, String openid) throws Exception {
		String reslut = HttpKit.post(TAG_GETID_URI.concat(accessToken), "{\"openid\":\"" + openid + "\"}");
		if (StringUtils.isNotEmpty(reslut)) {
			return JSONObject.parseObject(reslut);
		}
		return null;
	}

}
