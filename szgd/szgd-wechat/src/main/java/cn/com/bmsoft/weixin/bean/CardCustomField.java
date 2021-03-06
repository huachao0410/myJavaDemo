package cn.com.bmsoft.weixin.bean;

/**
 * Created by daniel on 2017/5/16.
 */
public class CardCustomField {

    /**
     * 会员信息类目半自定义名称，当开发者变更这类类目信息的value值时
     可以选择触发系统模板消息通知用户。
     FIELD_NAME_TYPE_LEVEL              等级
     FIELD_NAME_TYPE_COUPON        优惠券
     FIELD_NAME_TYPE_STAMP            印花
     FIELD_NAME_TYPE_DISCOUNT      折扣
     FIELD_NAME_TYPE_ACHIEVEMEN  成就
     FIELD_NAME_TYPE_MILEAGE          里程
     FIELD_NAME_TYPE_SET_POINTS     集点
     FIELD_NAME_TYPE_TIMS                次数
     */
    private String name_type;
    private String name;//会员信息类目自定义名称
    private String url;//点击类目跳转外链url

    public String getName_type() {
        return name_type;
    }

    public void setName_type(String name_type) {
        this.name_type = name_type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
