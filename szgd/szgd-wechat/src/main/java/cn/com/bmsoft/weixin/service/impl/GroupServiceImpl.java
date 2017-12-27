package cn.com.bmsoft.weixin.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.com.bmsoft.weixin.bean.Group;
import cn.com.bmsoft.weixin.bean.Groups;
import cn.com.bmsoft.weixin.service.IGroupService;
import cn.com.bmsoft.weixin.util.HttpKit;

import com.alibaba.fastjson.JSONObject;

/**
 * 微信会员组服务接口
 * 
 * @author daniel
 * 
 */
@Service
public class GroupServiceImpl implements IGroupService {
	
	private static final String GROUP_CREATE_URI = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=";
	private static final String GROUP_GET_URI = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=";
	private static final String GROUP_GETID_URI = "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=";
	private static final String GROUP_UPDATE_URI = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=";
	private static final String GROUP_MEMBERS_UPDATE_URI = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=";
	private static final String GROUP_DELETE_URI = "https://api.weixin.qq.com/cgi-bin/groups/delete?access_token=";

	/**
	 * 创建分组
	 * 
	 * @param accessToken
	 * @param name
	 *            分组名字（30个字符以内）
	 * @return
	 * @throws Exception
	 */
	@Override
	public Group create(String accessToken, String name) throws Exception {
		Map<String, Object> groupMap = new HashMap<String, Object>();
		Map<String, Object> nameObj = new HashMap<String, Object>();
		nameObj.put("name", name);
		groupMap.put("group", nameObj);
		String post = JSONObject.toJSONString(groupMap);
		String jsonStr = HttpKit.post(GROUP_CREATE_URI.concat(accessToken), post);
		if (StringUtils.isNotEmpty(jsonStr)) {
			JSONObject obj = JSONObject.parseObject(jsonStr);
			if (obj.get("errcode") != null) {
				throw new Exception(obj.getString("errmsg"));
			}
			Group group = JSONObject.toJavaObject(obj, Group.class);
			return group;
		}
		return null;
	}

	/**
	 * 查询所有分组
	 * 
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	@Override
	public Groups get(String accessToken) throws Exception {
		String jsonStr = HttpKit.get(GROUP_GET_URI.concat(accessToken));
		if (StringUtils.isNotEmpty(jsonStr)) {
			JSONObject obj = JSONObject.parseObject(jsonStr);
			if (obj.get("errcode") != null) {
				throw new Exception(obj.getString("errmsg"));
			}
			Groups groups = JSONObject.toJavaObject(obj, Groups.class);
			return groups;
		}
		return null;
	}

	/**
	 * 查询用户所在分组
	 * 
	 * @param accessToken
	 * @param openid
	 *            用户的OpenID
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject membersIn(String accessToken, String openid) throws Exception {
		String reslut = HttpKit.post(GROUP_GETID_URI.concat(accessToken), "{\"openid\":\"" + openid + "\"}");
		if (StringUtils.isNotEmpty(reslut)) {
			return JSONObject.parseObject(reslut);
		}
		return null;
	}

	/**
	 * 修改分组名
	 * 
	 * @param accessToken
	 * @param id
	 *            分组id，由微信分配
	 * @param name
	 *            分组名字（30个字符以内）
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject updateName(String accessToken, String id, String name) throws Exception {
		Map<String, Object> group = new HashMap<String, Object>();
		Map<String, Object> nameObj = new HashMap<String, Object>();
		nameObj.put("name", name);
		nameObj.put("id", id);
		group.put("group", nameObj);
		String post = JSONObject.toJSONString(group);
		String reslut = HttpKit.post(GROUP_UPDATE_URI.concat(accessToken), post);
		if (StringUtils.isNotEmpty(reslut)) {
			return JSONObject.parseObject(reslut);
		}
		return null;
	}

	/**
	 * 移动用户分组
	 * 
	 * @param accessToken
	 * @param openid
	 *            用户唯一标识符
	 * @param to_groupid
	 *            分组id
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject membersMove(String accessToken, String openid, String to_groupid) throws Exception {
		String strJson = "{\"openid\":\"" + openid + "\",\"to_groupid\":" + to_groupid + "}";
		String reslut = HttpKit.post(GROUP_MEMBERS_UPDATE_URI.concat(accessToken), strJson);
		if (StringUtils.isNotEmpty(reslut)) {
			return JSONObject.parseObject(reslut);
		}
		return null;
	}

	@Override
	public JSONObject delete(String accessToken, int grouid) throws Exception {
		String strJson = "{\"group\":{\"id\":" + grouid + "}}";
		String reslut = HttpKit.post(GROUP_DELETE_URI.concat(accessToken), strJson);
		if (StringUtils.isNotEmpty(reslut)) {
			return JSONObject.parseObject(reslut);
		}
		return null;
	}

}