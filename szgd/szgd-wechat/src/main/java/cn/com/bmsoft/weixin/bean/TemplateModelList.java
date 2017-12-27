package cn.com.bmsoft.weixin.bean;

import java.util.List;

/**
 * 消息模版列表
 * @author asus
 *
 */
public class TemplateModelList {
	private List<TemplateModel> template_list;
	private String access_token;
	
	public TemplateModelList() {
	}
	
	public TemplateModelList(List<TemplateModel> template_list,
			String access_token) {
		super();
		this.template_list = template_list;
		this.access_token = access_token;
	}

	public List<TemplateModel> getTemplate_list() {
		return template_list;
	}

	public void setTemplate_list(List<TemplateModel> template_list) {
		this.template_list = template_list;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	
}

