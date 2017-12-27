package cn.com.bmsoft.weixin.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.bmsoft.weixin.service.IOauthService;
import cn.com.bmsoft.weixin.service.IPayService;
import cn.com.bmsoft.weixin.util.ConfKit;
import cn.com.bmsoft.weixin.util.HttpKit;
/**
 * 微信Oauth和支付工具类
 *
 * @author L.cm
 * @date 2013-11-14 下午4:42:42
 */
@Service
public class OauthServiceImpl implements IOauthService {

    private static final String CODE_URI = "http://open.weixin.qq.com/connect/oauth2/authorize";
    private static final String TOKEN_URI = "https://api.weixin.qq.com/sns/oauth2/access_token";
    private static final String REFRESH_TOKEN_URI = "https://api.weixin.qq.com/sns/oauth2/refresh_token";

    @Autowired
    private IPayService payService;

    /**
     * 请求code
     * 
     * @return
     * @throws UnsupportedEncodingException 
     */
    public String getCode(String appid, String redirectUri) {
    	try {
    		Map<String, String> params = new HashMap<String, String>();
            params.put("appid", appid);
            params.put("response_type", "code");
            params.put("redirect_uri", URLEncoder.encode(redirectUri, "utf-8"));
            params.put("scope", "snsapi_base"); // snsapi_base（不弹出授权页面，只能拿到用户openid）snsapi_userinfo
            // （弹出授权页面，这个可以通过 openid 拿到昵称、性别、所在地）
            params.put("state", "wx#wechat_redirect");
            String para = payService.createSign(params, false);
            String url = CODE_URI + "?" + para;
            return url;
		} catch (Exception e) {
			return "";
		}
    }

    /**
     * 通过code 换取 access_token
     * 
     * @param code
     * @return
     * @throws IOException 
     * @throws NoSuchProviderException 
     * @throws NoSuchAlgorithmException 
     * @throws KeyManagementException 
     */
    public String getToken(String appid,String secret,String code) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", appid);
        params.put("secret", secret);
        params.put("code", code);
        params.put("grant_type", "authorization_code");
        return HttpKit.get(TOKEN_URI, params);
    }

    /**
     * 刷新 access_token
     * 
     * @param refreshToken
     * @return
     * @throws IOException 
     * @throws NoSuchProviderException 
     * @throws NoSuchAlgorithmException 
     * @throws KeyManagementException 
     */
    public String getRefreshToken(String appid,String refreshToken) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", appid);
        params.put("grant_type", "refresh_token");
        params.put("refresh_token", refreshToken);
        return HttpKit.get(REFRESH_TOKEN_URI, params);
    }

}
