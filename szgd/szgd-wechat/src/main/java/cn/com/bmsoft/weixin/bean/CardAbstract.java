package cn.com.bmsoft.weixin.bean;

/**
 * Created by daniel on 2017/5/16.
 */
public class CardAbstract {

    private String abstract_;
    private String[] icon_url_list;

    public String getAbstract() {
        return abstract_;
    }

    public void setAbstract(String abstract_) {
        this.abstract_ = abstract_;
    }

    public String[] getIcon_url_list() {
        return icon_url_list;
    }

    public void setIcon_url_list(String[] icon_url_list) {
        this.icon_url_list = icon_url_list;
    }
}
