package cn.com.bmsoft.dxms.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * T_ONLINE_BID 表的映射
 */
public class OnlineBid implements Serializable {

	private static final long serialVersionUID = 1L;
	//标识
	private int id;
	//代码
	private String code;
	//名称
	private String name;
	//名称
	private String category;
	//主管部门
	private String dept;
	//工作日
	private int workingDay;
	//在线申办URL
	private String onlineUrl;
	//表格下载ID
	private String downloadId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public int getWorkingDay() {
		return workingDay;
	}
	public void setWorkingDay(int workingDay) {
		this.workingDay = workingDay;
	}
	public String getOnlineUrl() {
		return onlineUrl;
	}
	public void setOnlineUrl(String onlineUrl) {
		this.onlineUrl = onlineUrl;
	}
	public String getDownloadId() {
		return downloadId;
	}
	public void setDownloadId(String downloadId) {
		this.downloadId = downloadId;
	}
  
}
