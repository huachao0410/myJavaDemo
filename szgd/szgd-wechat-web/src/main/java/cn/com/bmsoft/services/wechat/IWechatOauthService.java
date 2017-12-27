package cn.com.bmsoft.services.wechat;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 微信用户服务接口
 *
 * Created by daniel on 2017/5/4.
 */
@Controller
public interface IWechatOauthService {

    String getCodeRedirectUrl(String url);

    String getWechatOpenid(String code) throws Exception;

    String getAccessToken();

    Map<String, String> getJsapiSignature(HttpServletRequest request);

}
