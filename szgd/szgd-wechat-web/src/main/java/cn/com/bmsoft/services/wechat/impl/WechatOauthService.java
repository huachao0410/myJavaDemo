package cn.com.bmsoft.services.wechat.impl;

import cn.com.bmsoft.services.wechat.IWechatOauthService;
import cn.com.bmsoft.weixin.service.ICommonService;
import cn.com.bmsoft.weixin.service.IOauthService;
import cn.com.bmsoft.weixin.util.HttpKit;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 微信用户服务接口实现
 *
 * Created by daniel on 2017/5/4.
 */
@Service
public class WechatOauthService  implements IWechatOauthService {

    @Value("${wx_appid}")
    private String appid;

    @Value("${wx_appsecret}")
    private String appsecret;

    @Value("${getAccessTokenUrl}")
    private String getAccessTokenUrl;

    @Value("${getAccessTokenWay}")
    private String getAccessTokenWay;

    @Autowired
    private IOauthService oauthService;

    @Autowired
    private ICommonService commonService;

    @Override
    public String getCodeRedirectUrl(String url) {
        return oauthService.getCode(appid, url.toString());
    }

    @Override
    public String getWechatOpenid(String code) throws Exception {
        String token = this.oauthService.getToken(appid, appsecret, code);
        JSONObject obj = JSONObject.parseObject(token);
        return obj.get("openid") + "";
    }

    @Override
    public String getAccessToken() {
        String token = "";
        try {
            if(getAccessTokenWay.equals("config")) {
                token = this.commonService.getAccessToken(appid, appsecret);
            } else if(getAccessTokenWay.equals("service")){
                token = HttpKit.get(getAccessTokenUrl);
            }
        } catch (Exception e) {

        }
        return token;
    }

    @Override
    public Map<String, String> getJsapiSignature(HttpServletRequest request) {
        try {
            String url = request.getRequestURL().toString();
            Map<String, String> parameterMap = request.getParameterMap();
            if (parameterMap.keySet().size() > 0) {
                url += "?" + request.getQueryString();
            }

            String ticket = this.commonService.getTicket(getAccessToken());
            Map<String, String> signMap = this.commonService.sign(ticket, url);
            return signMap;
        } catch (Exception e) {
        }
        return null;
    }


}
