package cn.com.bmsoft.services.user.impl;

import cn.com.bmsoft.services.user.IWechatUserService;
import cn.com.bmsoft.weixin.util.HttpKit;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WechatUserServiceImpl implements IWechatUserService {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${userServiceUrl}")
	private String userServiceUrl;

	/**
	 * 检查是否登录
	 *
	 * @param openid
	 * @return
	 */
	@Override
	public boolean checkLogin(String openid) {
		//判断是否已登录
		try {
			String s=HttpKit.get(userServiceUrl + openid);
			Map<String, Object> jsonMap = JSONObject.parseObject(s);
			if(jsonMap.get("code").equals("200")){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			logger.error("检查是否登录", e);
		}
		return false;
	}

	/**
	 * 获取用户信息
	 *
	 * @param openid
	 * @return
	 */
	@Override
	public Map<String, Object> getUser(String openid) {
		try {
			String s = HttpKit.get(userServiceUrl + openid);
			Map<String, Object> map = JSONObject.parseObject(s);
			if(map.get("code").equals("200")){
				JSONArray ss=JSONObject.parseArray(map.get("datas").toString());
				if(ss.size()>0){
					Map<String, Object> jsonMap = JSONObject.parseObject(ss.getString(0));
					return jsonMap;
				}
			}
		} catch (Exception e) {
			logger.error("获取用户信息", e);
		}
		return null;
	}
}
