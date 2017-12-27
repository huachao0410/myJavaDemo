package cn.com.bmsoft.weixin.service.impl;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.com.bmsoft.weixin.service.IMenuService;
import cn.com.bmsoft.weixin.util.HttpKit;

import com.alibaba.fastjson.JSON;

/**
 * 菜单,可以将accessToken 存储在session或者memcache中
 */
@Service
public class MenuServiceImpl implements IMenuService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 

	private static final String MENU_URI = "https://api.weixin.qq.com/cgi-bin/menu";

	/**
	 * 创建菜单
	 * 
	 * @throws IOException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean createMenu(String accessToken, String params) throws Exception {
		String jsonStr = HttpKit.post(MENU_URI + "/create?access_token=" + accessToken, params);
		Map<String, Object> map = JSON.parseObject(jsonStr, Map.class);
		boolean result = "0".equals(map.get("errcode").toString());
		logger.info("创建微信菜单结果：" + map.get("errmsg"));
		return result;
	}

	/**
	 * 查询菜单
	 * 
	 * @throws IOException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> getMenuInfo(String accessToken) throws Exception {
		String jsonStr = HttpKit.get(MENU_URI + "/get?access_token=" + accessToken);
		Map<String, Object> map = JSON.parseObject(jsonStr, Map.class);
		return map;
	}

	/**
	 * 删除自定义菜单
	 * 
	 * @throws IOException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean deleteMenu(String accessToken) throws Exception {
		String jsonStr = HttpKit.get(MENU_URI + "/delete?access_token=" + accessToken);
		Map<String, Object> map = JSON.parseObject(jsonStr, Map.class);
		return "0".equals(map.get("errcode").toString());
	}
}
