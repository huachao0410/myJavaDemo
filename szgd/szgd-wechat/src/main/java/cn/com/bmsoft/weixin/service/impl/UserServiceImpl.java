package cn.com.bmsoft.weixin.service.impl;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cn.com.bmsoft.weixin.bean.UserInfo;
import cn.com.bmsoft.weixin.bean.UserList;
import cn.com.bmsoft.weixin.service.IUserService;
import cn.com.bmsoft.weixin.util.HttpKit;

import com.alibaba.fastjson.JSONObject;


/**
 * 用户操作接口
 */
@Service
public class UserServiceImpl implements IUserService {

	private static final String USER_INFO_URI = "https://api.weixin.qq.com/cgi-bin/user/info";
	private static final String USER_GET_URI = "https://api.weixin.qq.com/cgi-bin/user/get";
	private static final String USER_UPDATE_URI = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=";

	/**
	 * 拉取用户信息
	 * @param accessToken
	 * @param openid
	 * @return
	 * @throws IOException 
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	public UserInfo getUserInfo(String accessToken, String openid) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessToken);
		params.put("openid", openid);
		String  jsonStr = HttpKit.get(USER_INFO_URI, params);
		if(StringUtils.isNotEmpty(jsonStr)){
			JSONObject obj = JSONObject.parseObject(jsonStr);
			if(obj.get("errcode") != null){
				throw new Exception(obj.getString("errmsg"));
			}
			UserInfo user = JSONObject.toJavaObject(obj, UserInfo.class);
			return user;
		}
		return null;
	}
	
	/**
	 * 获取帐号的关注者列表
	 * @param accessToken
	 * @param next_openid
	 * @return
	 */
	public UserList getFollwersList(String accessToken, String next_openid) throws Exception{
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessToken);
		params.put("next_openid", next_openid);
		String  jsonStr = HttpKit.get(USER_GET_URI, params);
		if(StringUtils.isNotEmpty(jsonStr)){
			JSONObject obj = JSONObject.parseObject(jsonStr);
			if(obj.get("errcode") != null){
				throw new Exception(obj.getString("errmsg"));
			}
			UserList userList = JSONObject.toJavaObject(obj, UserList.class);
			return userList;
		}
		return null;
	}

	@Override
	public List<String> getUserOpenids(String accessToken, String next_openid) throws Exception {
		UserList userList = this.getFollwersList(accessToken, next_openid);
		List<String> ids = new ArrayList<String>();
		if(userList.getData() != null) {
			ids.addAll(Arrays.asList(userList.getData().getOpenid()));
		}
		if(userList.getNext_openid().length() > 1){
			ids.addAll(getUserOpenids(accessToken, userList.getNext_openid()));
		} 
		return ids;
	}

	@Override
	public JSONObject updateRemark(String accessToken, String openid, String remark) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("openid", openid);
		params.put("remark", remark);
		String post = JSONObject.toJSONString(params);
		String  jsonStr = HttpKit.post(USER_UPDATE_URI.concat(accessToken), post);
		if(StringUtils.isNotEmpty(jsonStr)){
			JSONObject obj = JSONObject.parseObject(jsonStr);
			if(obj.get("errcode") != null && !obj.get("errmsg").equals("ok")){
				throw new Exception(obj.getString("errmsg"));
			}
			return obj;
		}
		return null;
	}
	
}