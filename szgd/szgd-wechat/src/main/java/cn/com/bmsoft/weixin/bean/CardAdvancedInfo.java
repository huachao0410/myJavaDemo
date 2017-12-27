package cn.com.bmsoft.weixin.bean;

/**
 * 卡券对象(高级信息)
 * Created by daniel on 2017/5/16.
 */
public class CardAdvancedInfo {

    private CardUseCondition use_condition = new CardUseCondition();
    private CardAbstract abstract_ = new CardAbstract();
    private CardTextImage[] text_image_list;
    private CardTimeLimit[] time_limit;
    private String[] business_service;

    public CardAbstract getAbstract() {
        return abstract_;
    }

    public void setAbstract(CardAbstract abstract_) {
        this.abstract_ = abstract_;
    }

    public String[] getBusiness_service() {
        return business_service;
    }

    public void setBusiness_service(String[] business_service) {
        this.business_service = business_service;
    }

    public CardTextImage[] getText_image_list() {
        return text_image_list;
    }

    public void setText_image_list(CardTextImage[] text_image_list) {
        this.text_image_list = text_image_list;
    }

    public CardTimeLimit[] getTime_limit() {
        return time_limit;
    }

    public void setTime_limit(CardTimeLimit[] time_limit) {
        this.time_limit = time_limit;
    }

    public CardUseCondition getUse_condition() {
        return use_condition;
    }

    public void setUse_condition(CardUseCondition use_condition) {
        this.use_condition = use_condition;
    }
}
