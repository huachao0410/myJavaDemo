package cn.com.bmsoft.weixin.bean;

/**
 * 回复用户消息基础类
 * 
 * @author daniel
 *
 */
public class OutMessage {

	private String ToUserName;//接收方帐号（收到的OpenID）
	private String FromUserName;//开发者微信号
	private Long CreateTime;//消息创建时间 （整型）
	private int FuncFlag = 0;

	public String getToUserName() {
		return ToUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public Long getCreateTime() {
		return CreateTime;
	}

	public int getFuncFlag() {
		return FuncFlag;
	}

	public void setFuncFlag(int funcFlag) {
		FuncFlag = funcFlag;
	}
}