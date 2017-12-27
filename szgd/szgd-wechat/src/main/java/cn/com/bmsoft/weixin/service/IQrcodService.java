package cn.com.bmsoft.weixin.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import com.alibaba.fastjson.JSONObject;

/**
 * 微信二维码服务
 * 
 * @author daniel
 * 
 */
public interface IQrcodService {

	/**
	 * 创建临时二维码
	 * 
	 * @param accessToken
	 * @param expireSeconds
	 *            最大不超过1800
	 * @param sceneId
	 *            场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
	 * @return
	 * @throws Exception
	 */
	public JSONObject createScene(String accessToken, int expireSeconds, int sceneId) throws Exception;

	/**
	 * 创建永久二维码
	 * 
	 * @param accessToken
	 * @param sceneId
	 * @return
	 * @throws Exception
	 */
	public JSONObject createLimitScene(String accessToken, int sceneId) throws Exception;

	/**
	 * 获取查看二维码链接
	 * 
	 * @param ticket
	 * @return
	 */
	public String showqrcodeUrl(String ticket);

}
