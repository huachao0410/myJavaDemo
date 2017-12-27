package cn.com.bmsoft.weixin.bean;

public class UserList {

	private int total;
	private int count;
	private WxData data;
	private String next_openid;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getNext_openid() {
		return next_openid;
	}

	public void setNext_openid(String next_openid) {
		this.next_openid = next_openid;
	}

	public WxData getData() {
		return data;
	}

	public void setData(WxData data) {
		this.data = data;
	}

}
