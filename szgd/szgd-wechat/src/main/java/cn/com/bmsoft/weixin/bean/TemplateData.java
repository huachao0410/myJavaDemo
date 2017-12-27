package cn.com.bmsoft.weixin.bean;

import java.util.HashMap;

/**
 * 模板消息数据对象
 *
 */
public class TemplateData {

    private String touser; //接收者openid
    private String template_id; //模板ID
    private String url;  //模板跳转链接
    private String topcolor;  
    private Miniprogram miniprogram; //跳小程序所需数据，不需跳小程序可不用传该数据
    private TemplateDataItem data = new TemplateDataItem(); //模板数据

	public TemplateData(String touser, String template_id, String url,
			String topcolor, Miniprogram miniprogram, TemplateDataItem data) {
		super();
		this.touser = touser;
		this.template_id = template_id;
		this.url = url;
		this.topcolor = topcolor;
		this.miniprogram = miniprogram;
		this.data = data;
	}

	public TemplateData(String touser, String template_id, String url, String topcolor) {
        this.touser = touser;
        this.template_id = template_id;
        this.url = url;
        this.topcolor = topcolor;
    }

    public TemplateData(String touser, String template_id) {
        this.touser = touser;
        this.template_id = template_id;
    }

    public TemplateData(String touser, String template_id, String url) {
        this.touser = touser;
        this.template_id = template_id;
        this.url = url;
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopcolor() {
        return topcolor;
    }

    public void setTopcolor(String topcolor) {
        this.topcolor = topcolor;
    }
    
	public Miniprogram getMiniprogram() {
		return miniprogram;
	}

	public void setMiniprogram(Miniprogram miniprogram) {
		this.miniprogram = miniprogram;
	}

	public TemplateDataItem getData() {
        return data;
    }

    public void setData(TemplateDataItem data) {
        this.data = data;
    }

    @Deprecated
    public TemplateDataItem getTemplateDataItemInstance() {
        return getData();
    }

    public TemplateData push(String key, String value, String color) {
        this.data.addItem(key, value, color);
        return this;
    }

    public TemplateData push(String key, String value) {
        this.data.addItem(key, value);
        return this;
    }

    public class TemplateDataItem extends HashMap<String, Item> {
        private static final long serialVersionUID = 1L;

        public Item getItemInstance(String value) {
            return new Item(value);
        }

        public Item getItemInstance(String value, String color) {
            return new Item(value, color);
        }

        public TemplateDataItem() {
        }

        public TemplateDataItem addItem(String key, String value) {
            this.put(key, new Item(value));
            return this;
        }

        public TemplateDataItem addItem(String key, String value, String color) {
            this.put(key, new Item(value, color));
            return this;
        }
    }

    public class Item {
        private String value;
        private String color = "#000000";

        public Item(String value) {
            this.value = value;
        }

        public Item(String value, String color) {
            this.value = value;
            this.color = color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getColor() {
            return color;
        }
    }
    
    public class Miniprogram {
    	private String appid="";  //所需跳转到的小程序appid（该小程序appid必须与发模板消息的公众号是绑定关联关系）
	    private String pagepath=""; //所需跳转到小程序的具体页面路径，支持带参数,（示例index?foo=bar）
	    
	    public Miniprogram() {
		}
	    
		public Miniprogram(String appid, String pagepath) {
			super();
			this.appid = appid;
			this.pagepath = pagepath;
		}

		public String getAppid() {
			return appid;
		}

		public void setAppid(String appid) {
			this.appid = appid;
		}

		public String getPagepath() {
			return pagepath;
		}

		public void setPagepath(String pagepath) {
			this.pagepath = pagepath;
		}
	    
	    
    }
}
