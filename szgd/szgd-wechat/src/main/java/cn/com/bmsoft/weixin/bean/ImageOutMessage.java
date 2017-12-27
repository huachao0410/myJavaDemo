package cn.com.bmsoft.weixin.bean;

/**
 * 回复图片消息
 * 
 * @author daniel
 * 
 */
public class ImageOutMessage extends OutMessage {

	private String MsgType = "image"; //

	private Image Image;

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public Image getImage() {
		return Image;
	}

	public void setImage(Image image) {
		Image = image;
	}

}
