package cn.com.bmsoft.weixin.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by gzh on 2017/7/27.
 * 黑名单管理
 */
public interface IBlackService {

	/**
	 * 1. 获取公众号的黑名单列表
	 *
	 * @param accessToken
	 * @param begin_openid
	 * @return
	 * @throws Exception
	 */
	public JSONObject getBlacklist(String accessToken, String begin_openid) throws Exception;

	/**
	 * 2. 拉黑用户
	 *
	 * @param accessToken
	 * @param opened_list
	 * @return
	 * @throws Exception
	 */
	public JSONObject batchBlackList(String accessToken, List<String> opened_list) throws Exception;

	/**
	 * 3. 取消拉黑用户
	 *
	 * @param accessToken
	 * @param opened_list
	 * @return
	 * @throws Exception
	 */
	public JSONObject batchUnblackList(String accessToken, List<String> opened_list) throws Exception;

}
