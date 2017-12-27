package cn.com.bmsoft.weixin.bean;

import java.io.Serializable;

/**
 * 回复图片类
 * 
 * @author daniel
 * 
 */
public class Image implements Serializable {

	private static final long serialVersionUID = 1L;

	private String MediaId;// 通过素材管理接口上传多媒体文件，得到的id。

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

}
