package cn.com.bmsoft.weixin.service;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.com.bmsoft.weixin.bean.Attachment;
import cn.com.bmsoft.weixin.bean.InMessage;


/**
 * 微信常用服务接口
 * 
 * @author daniel
 * 
 */
public interface ICommonService {

	/**
	 * 获取微信访问令牌
	 * 
	 * @param appid
	 * @param secret
	 * @return
	 * @throws Exception
	 */
	public String getAccessToken(String appid, String secret) throws Exception;


	/**
	 * 获取授权token
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public String getOauth2Token(String appid, String appsecret,String code) throws Exception;
	/**
	 * 获取微信服务器IP地址
	 *
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public boolean getCallBackip(String accessToken) throws Exception;
	/**
	 * 支付反馈
	 * 
	 * @param openid
	 * @param feedbackid
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public boolean payfeedback(String openid, String feedbackid, String accessToken) throws Exception;
	
	/**
	 * 消息体转换
	 * 
	 * @param responseInputString
	 * @return
	 * @throws Exception
	 */
	public InMessage parsingInMessage(String responseInputString) throws Exception;
	
	/**
     * 根据接收到用户消息进行处理
     *
     * @param responseInputString 微信发送过来的xml消息体
     * @return
     */
	public String processing(String responseInputString) throws Exception;
	
	/**
     * 根据接收到用户消息进行处理
     *
     * @param inMessage 消息体
     * @return
     */
	public String processing(InMessage inMessage) throws Exception;
	
	
	/**
	 * 获得jsapi_ticket（有效期7200秒)
	 * 
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public String getTicket(String accessToken) throws Exception;

	/**
	 * 获得wx_card_ticket（有效期7200秒)
	 *
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public String getWxCardTicket(String accessToken) throws Exception;
	
	/**
	 * 判断是否来自微信, 5.0 之后的支持微信支付
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
    public boolean isWeiXin(HttpServletRequest request) throws Exception;
    
    /**
     * 获取媒体资源
     * 
     * @param accessToken
     * @param mediaId
     * @return
     * @throws Exception
     */
    public Attachment getMedia(String accessToken, String mediaId) throws Exception;
    
    /**
     * 上传素材文件
     * 
     * @param accessToken
     * @param type
     * @param file
     * @return
     * @throws Exception
     */
    public Map<String, Object> uploadMedia(String accessToken, String type, File file) throws Exception;

	/**
	 * 签名
	 *
	 * @param ticket
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> sign(String ticket, String url) throws Exception;
    
}
