package cn.com.bmsoft.weixin.service.impl;

import cn.com.bmsoft.weixin.service.IBlackService;
import cn.com.bmsoft.weixin.util.HttpKit;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gzh on 2017/7/27.
 * 黑名单服务管理类
 */
@Service
public class BlackServiceImpl implements IBlackService{
	/**
	 *1. 获取公众号的黑名单列表
	 */
	private static final String BLACK_LIST_URI = "https://api.weixin.qq.com/cgi-bin/tags/members/getblacklist?access_token=";
	/**
	 *2. 拉黑用户
	 */
	private static final String BATCH_BLACK_URI = "https://api.weixin.qq.com/cgi-bin/tags/members/batchblacklist?access_token=";
	/**
	 *3. 取消拉黑用户
	 */
	private static final String UNBLACK_BLACK_URI = "https://api.weixin.qq.com/cgi-bin/tags/members/batchunblacklist?access_token=";

	@Override
	public JSONObject getBlacklist(String accessToken, String begin_openid) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin_openid", begin_openid);
		String post = JSONObject.toJSONString(map);
		String jsonStr = HttpKit.post(BLACK_LIST_URI.concat(accessToken), post);
		if (StringUtils.isNotEmpty(jsonStr)) {
			JSONObject obj = JSONObject.parseObject(jsonStr);
			return obj;
		}
		return null;
	}

	@Override
	public JSONObject batchBlackList(String accessToken, List<String> opened_list) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("opened_list", opened_list);
		String post = JSONObject.toJSONString(map);
		String jsonStr = HttpKit.post(BATCH_BLACK_URI.concat(accessToken), post);
		if (StringUtils.isNotEmpty(jsonStr)) {
			JSONObject obj = JSONObject.parseObject(jsonStr);
			return obj;
		}
		return null;
	}

	@Override
	public JSONObject batchUnblackList(String accessToken, List<String> opened_list) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("opened_list", opened_list);
		String post = JSONObject.toJSONString(map);
		String jsonStr = HttpKit.post(UNBLACK_BLACK_URI.concat(accessToken), post);
		if (StringUtils.isNotEmpty(jsonStr)) {
			JSONObject obj = JSONObject.parseObject(jsonStr);
			return obj;
		}
		return null;
	}
}
