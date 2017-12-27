package cn.com.bmsoft.weixin.service.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;

import cn.com.bmsoft.weixin.bean.Articles;
import cn.com.bmsoft.weixin.bean.Attachment;
import cn.com.bmsoft.weixin.bean.InMessage;
import cn.com.bmsoft.weixin.bean.OutMessage;
import cn.com.bmsoft.weixin.inf.MessageProcessingHandler;
import cn.com.bmsoft.weixin.service.ICommonService;
import cn.com.bmsoft.weixin.util.HttpKit;
import cn.com.bmsoft.weixin.util.XStreamFactory;

/**
 * 微信公共服务
 *
 * @author daniel
 */
@Service
public class CommonServiceImpl implements ICommonService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
	private static final String OAUTH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?";
	private static final String OAUTH_REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?";

	private static final String OAUTH_USERINFO = "https://api.weixin.qq.com/sns/userinfo?access_token=";

	private static final String PAYFEEDBACK_URL = "https://api.weixin.qq.com/payfeedback/update";
	private static final String GET_MEDIA_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=";
	private static final String UPLOAD_MEDIA_URL = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=";
	private static final String JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=";
	private static final String WX_CARD_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=wx_card&access_token=";
	private static final String WX_GET_IPLIST = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=";


	@Autowired
	private MessageProcessingHandler messageProcessingHandler;

	private Map<String, String> mapTokenSession = new HashMap<String, String>();// 缓存住微信访问token
	private Map<String, Long> mapTokenTime = new HashMap<String, Long>();// 缓存住微信访问token的时间，小于 7200秒

	@Override
	public String getAccessToken(String appid, String secret) throws Exception {
		String accessToken = mapTokenSession.get(appid);
		if (accessToken != null) {
			Long time = mapTokenTime.get(accessToken);
			if ((System.currentTimeMillis() - time.longValue()) / 1000 > 7180) {//超时啦
				mapTokenTime.remove(accessToken);
				return accessToken(appid, secret);
			} else {
				boolean isInvalid = getCallBackip(accessToken);
				if (isInvalid) {
					return accessToken(appid, secret);
				} else {
					return accessToken;
				}
			}
		} else { // 不存在token
			return accessToken(appid, secret);
		}
	}

	/**
	 * 获取授权用户信息
	 *
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getOauth2Token(String appid, String appsecret, String code) throws Exception {
		String sessionCode = mapTokenSession.get(appid + "code");
		if (sessionCode != null && sessionCode.equals(code)) {
			//拉取用户信息
			String refresh_token = mapTokenSession.get(appid + "refresh_token");
			return oauth2RefreshToken(appid, refresh_token);

		} else {
			String jsonStr = HttpKit.get(OAUTH_TOKEN_URL.concat("&appid=") + appid + "&secret=" + appsecret + "&code=" + code + "&grant_type=authorization_code");
			JSONObject jsonObject = JSON.parseObject(jsonStr);
			String rs_access_token = jsonObject.getString("access_token");
			String refresh_token = jsonObject.getString("refresh_token");
			String rs_openid = jsonObject.getString("openid");
			if (rs_access_token != null && rs_openid != null) {
				mapTokenSession.put(appid + "code", code);
				mapTokenSession.put(appid + "refresh_token", refresh_token);
				return getOauthUserInfo(rs_access_token, rs_openid);
			} else {
				return jsonStr;
			}
		}
	}

	private String oauth2RefreshToken(String appid, String refreshToken) throws Exception {
		String jsonStr = HttpKit.get(OAUTH_REFRESH_TOKEN_URL.concat("&appid=") + appid + "&grant_type=refresh_token&refresh_token=" + refreshToken);
		JSONObject jsonObject = JSON.parseObject(jsonStr);
		String rs_access_token = jsonObject.getString("access_token");
		String rs_openid = jsonObject.getString("openid");
		if (rs_access_token != null && rs_openid != null) {
			return getOauthUserInfo(rs_access_token, rs_openid);
		} else {
			return jsonStr;
		}
	}

	/**
	 * 根据授权token获取用户信息
	 *
	 * @param access_token
	 * @param openid
	 * @return
	 */
	private String getOauthUserInfo(String access_token, String openid) throws Exception {
		String jsonStr = HttpKit.get(OAUTH_USERINFO.concat(access_token) + "&openid=" + openid + "&lang=zh_CN");
		return jsonStr;
	}

	@Override
	public boolean getCallBackip(String accessToken) throws Exception {
		String jsonStr = HttpKit.get(WX_GET_IPLIST.concat(accessToken));
		//{"errcode":40001,"errmsg":"invalid credential, access_token is invalid or not latest hint: [K.u0368vr57!]"}
		net.sf.json.JSONObject json_result = net.sf.json.JSONObject.fromObject(jsonStr);
		if (json_result != null && json_result.get("errcode") != null) {
			return true;
		}
		return false;
	}


	private String accessToken(String appid, String secret) throws Exception {
		String jsonStr = HttpKit.get(ACCESSTOKEN_URL.concat("&appid=") + appid + "&secret=" + secret);
		Map<String, Object> map = JSONObject.parseObject(jsonStr);
		String accessToken = map.get("access_token").toString();
		mapTokenSession.put(appid, accessToken);
		mapTokenTime.put(accessToken, System.currentTimeMillis());
		return accessToken;
	}

	@Override
	public boolean payfeedback(String openid, String feedbackid, String accessToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("access_token", accessToken);
		map.put("openid", openid);
		map.put("feedbackid", feedbackid);
		String jsonStr = HttpKit.get(PAYFEEDBACK_URL, map);
		Map<String, Object> jsonMap = JSONObject.parseObject(jsonStr);
		return "0".equals(jsonMap.get("errcode").toString());
	}

	/**
	 * 消息体转换
	 *
	 * @param responseInputString
	 * @return
	 */
	@Override
	public InMessage parsingInMessage(String responseInputString) {
		logger.info("responseInputString:\n" + responseInputString);
		//转换微信post过来的xml内容
		XStream xs = XStreamFactory.init(false);
//        xs.ignoreUnknownElements();
		xs.alias("xml", InMessage.class);
		InMessage msg = (InMessage) xs.fromXML(responseInputString);
		return msg;
	}

	@Override
	public String processing(InMessage inMessage) throws Exception {
		OutMessage oms = null;
		String xml = "";
		try {
			//取得消息类型
			String type = inMessage.getMsgType();
			Method getOutMessage = messageProcessingHandler.getClass().getMethod("getOutMessage");
			Method allTypeMethod = messageProcessingHandler.getClass().getMethod("allType", InMessage.class);
			Method typeMethod = messageProcessingHandler.getClass().getMethod(type + "TypeMsg", InMessage.class);

			allTypeMethod.invoke(messageProcessingHandler, inMessage);

			if (typeMethod != null) {
				typeMethod.invoke(messageProcessingHandler, inMessage);
			}

			Object obj = getOutMessage.invoke(messageProcessingHandler);
			if (obj != null) {
				oms = (OutMessage) obj;
			}
			//调用事后处理
			try {
				Method aftMt = messageProcessingHandler.getClass().getMethod("afterProcess", InMessage.class, OutMessage.class);
				aftMt.invoke(messageProcessingHandler, inMessage, oms);
			} catch (Exception e) {
			}

			obj = getOutMessage.invoke(messageProcessingHandler);
			if (obj != null) {
				oms = (OutMessage) obj;
				setMsgInfo(oms, inMessage);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if (oms != null) {
			// 把发送发送对象转换为xml输出
			XStream xs = XStreamFactory.init(true);
			xs.alias("xml", oms.getClass());
			xs.alias("item", Articles.class);
			xml = xs.toXML(oms);
		}
		return xml;
	}


	@Override
	public String processing(String responseInputString) throws Exception {
		InMessage inMessage = parsingInMessage(responseInputString);
		return processing(inMessage);
	}

	/**
	 * 设置发送消息体
	 *
	 * @param oms
	 * @param msg
	 * @throws Exception
	 */
	private void setMsgInfo(OutMessage oms, InMessage msg) throws Exception {
		if (oms != null) {
			Class<?> outMsg = oms.getClass().getSuperclass();
			Field CreateTime = outMsg.getDeclaredField("CreateTime");
			Field ToUserName = outMsg.getDeclaredField("ToUserName");
			Field FromUserName = outMsg.getDeclaredField("FromUserName");

			ToUserName.setAccessible(true);
			CreateTime.setAccessible(true);
			FromUserName.setAccessible(true);

			CreateTime.set(oms, new Date().getTime());
			ToUserName.set(oms, msg.getFromUserName());
			FromUserName.set(oms, msg.getToUserName());
		}
	}

	@Override
	public String getTicket(String accessToken) throws Exception {
		String jsonStr = HttpKit.get(JSAPI_TICKET.concat(accessToken));
		JSONObject obj = JSONObject.parseObject(jsonStr);
		if (obj.get("errcode") != null && obj.getInteger("errcode").intValue() != 0) {
			throw new Exception(obj.getString("errmsg"));
		}
		return obj.getString("ticket");
	}

	@Override
	public String getWxCardTicket(String accessToken) throws Exception {
		String jsonStr = HttpKit.get(WX_CARD_TICKET.concat(accessToken));
		JSONObject obj = JSONObject.parseObject(jsonStr);
		if (obj.get("errcode") != null && obj.getInteger("errcode").intValue() != 0) {
			throw new Exception(obj.getString("errmsg"));
		}
		return obj.getString("ticket");
	}

	@Override
	public boolean isWeiXin(HttpServletRequest request) throws Exception {
		String userAgent = request.getHeader("User-Agent");
		if (StringUtils.isNotBlank(userAgent)) {
			Pattern p = Pattern.compile("MicroMessenger/(\\d+).+");
			Matcher m = p.matcher(userAgent);
			String version = null;
			if (m.find()) {
				version = m.group(1);
			}
			return (null != version && NumberUtils.toInt(version) >= 5);
		}
		return false;
	}

	@Override
	public Attachment getMedia(String accessToken, String mediaId) throws Exception {
		String url = GET_MEDIA_URL + accessToken + "&media_id=" + mediaId;
		return HttpKit.download(url);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> uploadMedia(String accessToken, String type, File file) throws Exception {
		String url = UPLOAD_MEDIA_URL + accessToken + "&type=" + type;
		String jsonStr = HttpKit.upload(url, file);
		return JSON.parseObject(jsonStr, Map.class);
	}

	@Override
	public Map<String, String> sign(String ticket, String url) throws Exception {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";

		//注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + ticket +
				"&noncestr=" + nonce_str +
				"&timestamp=" + timestamp +
				"&url=" + url;

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);

		return ret;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}


}
