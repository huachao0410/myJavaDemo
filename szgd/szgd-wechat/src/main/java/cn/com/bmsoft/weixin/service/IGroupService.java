package cn.com.bmsoft.weixin.service;

import cn.com.bmsoft.weixin.bean.Group;
import cn.com.bmsoft.weixin.bean.Groups;

import com.alibaba.fastjson.JSONObject;

/**
 * 微信组服务接口
 * 
 * @author daniel
 * 
 */
public interface IGroupService {

	/**
	 * 创建分组
	 * 
	 * @param accessToken
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Group create(String accessToken, String name) throws Exception;

	/**
	 * 查询所有分组
	 * 
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public Groups get(String accessToken) throws Exception;

	/**
	 * 查询用户所在分组
	 * 
	 * @param accessToken
	 * @param openid
	 * @return
	 * @throws Exception
	 */
	public JSONObject membersIn(String accessToken, String openid) throws Exception;

	/**
	 * 修改分组名
	 * 
	 * @param accessToken
	 * @param id
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public JSONObject updateName(String accessToken, String id, String name) throws Exception;

	/**
	 * 移动用户分组
	 * 
	 * @param accessToken
	 * @param openid
	 * @param to_groupid
	 * @return
	 * @throws Exception
	 */
	public JSONObject membersMove(String accessToken, String openid, String to_groupid) throws Exception;

	/**
	 * 删除分组
	 * 
	 * @param accessToken
	 * @param grouid
	 * @return
	 * @throws Exception
	 */
	public JSONObject delete(String accessToken, int grouid) throws Exception;

}
