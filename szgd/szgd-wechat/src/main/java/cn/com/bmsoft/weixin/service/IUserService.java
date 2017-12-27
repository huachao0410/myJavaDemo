package cn.com.bmsoft.weixin.service;

import java.util.List;

import cn.com.bmsoft.weixin.bean.UserInfo;
import cn.com.bmsoft.weixin.bean.UserList;

import com.alibaba.fastjson.JSONObject;

/**
 * 微信用户服务接口
 * 
 * @author daniel
 *
 */
public interface IUserService {
	
	public UserInfo getUserInfo(String accessToken, String openid) throws Exception;
	
	public UserList getFollwersList(String accessToken, String next_openid) throws Exception;
	
	public List<String> getUserOpenids(String accessToken, String next_openid) throws Exception;
	
	public JSONObject updateRemark(String accessToken, String openid, String remark) throws Exception;

}
