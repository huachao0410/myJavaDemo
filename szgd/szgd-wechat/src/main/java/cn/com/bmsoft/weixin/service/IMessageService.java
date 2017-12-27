package cn.com.bmsoft.weixin.service;

import java.util.List;

import cn.com.bmsoft.weixin.bean.Article;
import cn.com.bmsoft.weixin.bean.Articles;
import cn.com.bmsoft.weixin.bean.TemplateData;
import cn.com.bmsoft.weixin.inf.SendAllMsgTypes;

import com.alibaba.fastjson.JSONObject;

/**
 * 微信信息服务
 * 
 * @author daniel
 * 
 */
public interface IMessageService {

	/**
	 * 发送文本客服消息
	 * 
	 * @param accessToken
	 * @param openId
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public String sendText(String accessToken, String openId, String text) throws Exception;

	/**
	 * 发送图片消息
	 * 
	 * @param accessToken
	 * @param openId
	 * @param media_id
	 * @return
	 * @throws Exception
	 */
	public String SendImage(String accessToken, String openId, String media_id) throws Exception;

	/**
	 * 发送语言回复
	 * 
	 * @param accessToken
	 * @param openId
	 * @param media_id
	 * @return
	 * @throws Exception
	 */
	public String SendVoice(String accessToken, String openId, String media_id) throws Exception;

	/**
	 * 发送视频回复
	 * 
	 * @param accessToken
	 * @param openId
	 * @param media_id
	 * @param title
	 * @param description
	 * @return
	 * @throws Exception
	 */
	public String SendVideo(String accessToken, String openId, String media_id, String title, String description)
			throws Exception;

	/**
	 * 发送音乐回复
	 * 
	 * @param accessToken
	 * @param openId
	 * @param musicurl
	 * @param hqmusicurl
	 * @param thumb_media_id
	 * @param title
	 * @param description
	 * @return
	 * @throws Exception
	 */
	public String SendMusic(String accessToken, String openId, String musicurl, String hqmusicurl,
			String thumb_media_id, String title, String description) throws Exception;

	/**
	 * 发送图文回复
	 * 
	 * @param accessToken
	 * @param openId
	 * @param articles
	 * @return
	 * @throws Exception
	 */
	public String SendNews(String accessToken, String openId, List<Articles> articles) throws Exception;

	/**
	 * 上传图文消息素材
	 * 
	 * @param accessToken
	 * @param articles
	 * @return
	 * @throws Exception
	 */
	public JSONObject uploadnews(String accessToken, List<Article> articles) throws Exception;

	/**
     * 群发消息
     * @param accessToken   token
     * @param type          群发消息类型
     * @param content       内容
     * @param title         类型是video是有效
     * @param description   类型是video是有效
     * @param groupId       发送目标对象的群组id
     * @param openids       发送目标对象的openid类表
     * @param toAll         是否发送给全部人
     * @return
     * @throws Exception
     */
	public JSONObject massSendall(String accessToken, SendAllMsgTypes type, String content, String title,
			String description, String groupId, String[] openids, boolean toAll) throws Exception;

	/**
	 * 删除群发
	 * 
	 * @param accessToken
	 * @param msgid
	 * @return
	 * @throws Exception
	 */
	public JSONObject massSend(String accessToken, String msgid) throws Exception;

	/**
	 * 发送模板消息
	 * 
	 * @param accessToken
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public JSONObject templateSend(String accessToken, TemplateData data) throws Exception;

}
