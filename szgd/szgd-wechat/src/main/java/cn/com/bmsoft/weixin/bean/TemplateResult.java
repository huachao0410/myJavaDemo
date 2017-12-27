package cn.com.bmsoft.weixin.bean;

/**
 * 微信推送返回结果
 * @author asus
 *
 */
public class TemplateResult {
	private String toUsername; //微信公众号
	private String fromUsername; //接收推送模版用户openid
	private String createTime; //创建时间
	private String msgType; //消息类型
	private String event; //事件
	private String msgId; //消息id
	private String status; //发送状态
	
	public TemplateResult() {
	}

	public TemplateResult(String toUsername, String fromUsername,
			String createTime, String msgType, String event, String msgId,
			String status) {
		super();
		this.toUsername = toUsername;
		this.fromUsername = fromUsername;
		this.createTime = createTime;
		this.msgType = msgType;
		this.event = event;
		this.msgId = msgId;
		this.status = status;
	}

	public String getToUsername() {
		return toUsername;
	}

	public void setToUsername(String toUsername) {
		this.toUsername = toUsername;
	}

	public String getFromUsername() {
		return fromUsername;
	}

	public void setFromUsername(String fromUsername) {
		this.fromUsername = fromUsername;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
