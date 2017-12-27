package cn.com.bmsoft.weixin.bean;

/**
 * 卡券对象(基本信息)
 * Created by daniel on 2017/5/16.
 */
public class CardBaseInfo {

    private String logo_url;
    private String brand_name;
    /**
     Code展示类型，
     "CODE_TYPE_TEXT"文本
     "CODE_TYPE_BARCODE"一维码
     "CODE_TYPE_QRCODE"二维码
     "CODE_TYPE_ONLY_QRCODE"仅显示二维码
     "CODE_TYPE_ONLY_BARCODE"仅显示一维码
     "CODE_TYPE_NONE"不显示任何码型
     */
    private String code_type = "CODE_TYPE_TEXT";
    private String title;
    private String color;
    private String notice;
    private String service_phone;
    private String description;
    private CardDateInfo date_info = new CardDateInfo();
    private CardSku sku = new CardSku();
    private Integer use_limit;
    private Integer get_limit;
    private Boolean use_custom_code;
    private Boolean bind_openid;
    private Boolean can_share;
    private Boolean can_give_friend;
    private Integer[] location_id_list;
    private String center_title;
    private String center_sub_title;
    private String center_url;
    private String custom_url_name;
    private String custom_url;
    private String custom_url_sub_title;
    private String promotion_url_name;
    private String promotion_url;
    private String source;
    private Boolean need_push_on_view;

    public Boolean isNeed_push_on_view() {
        return need_push_on_view;
    }

    public void setNeed_push_on_view(Boolean need_push_on_view) {
        this.need_push_on_view = need_push_on_view;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getCode_type() {
        return code_type;
    }

    public void setCode_type(String code_type) {
        this.code_type = code_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getService_phone() {
        return service_phone;
    }

    public void setService_phone(String service_phone) {
        this.service_phone = service_phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CardDateInfo getDate_info() {
        return date_info;
    }

    public void setDate_info(CardDateInfo date_info) {
        this.date_info = date_info;
    }

    public CardSku getSku() {
        return sku;
    }

    public void setSku(CardSku sku) {
        this.sku = sku;
    }

    public Integer getUse_limit() {
        return use_limit;
    }

    public void setUse_limit(Integer use_limit) {
        this.use_limit = use_limit;
    }

    public Integer getGet_limit() {
        return get_limit;
    }

    public void setGet_limit(Integer get_limit) {
        this.get_limit = get_limit;
    }

    public Boolean isUse_custom_code() {
        return use_custom_code;
    }

    public void setUse_custom_code(Boolean use_custom_code) {
        this.use_custom_code = use_custom_code;
    }

    public Boolean isBind_openid() {
        return bind_openid;
    }

    public void setBind_openid(Boolean bind_openid) {
        this.bind_openid = bind_openid;
    }

    public Boolean isCan_share() {
        return can_share;
    }

    public void setCan_share(Boolean can_share) {
        this.can_share = can_share;
    }

    public Boolean isCan_give_friend() {
        return can_give_friend;
    }

    public void setCan_give_friend(Boolean can_give_friend) {
        this.can_give_friend = can_give_friend;
    }

    public Integer[] getLocation_id_list() {
        return location_id_list;
    }

    public void setLocation_id_list(Integer[] location_id_list) {
        this.location_id_list = location_id_list;
    }

    public String getCenter_title() {
        return center_title;
    }

    public void setCenter_title(String center_title) {
        this.center_title = center_title;
    }

    public String getCenter_sub_title() {
        return center_sub_title;
    }

    public void setCenter_sub_title(String center_sub_title) {
        this.center_sub_title = center_sub_title;
    }

    public String getCenter_url() {
        return center_url;
    }

    public void setCenter_url(String center_url) {
        this.center_url = center_url;
    }

    public String getCustom_url_name() {
        return custom_url_name;
    }

    public void setCustom_url_name(String custom_url_name) {
        this.custom_url_name = custom_url_name;
    }

    public String getCustom_url() {
        return custom_url;
    }

    public void setCustom_url(String custom_url) {
        this.custom_url = custom_url;
    }

    public String getCustom_url_sub_title() {
        return custom_url_sub_title;
    }

    public void setCustom_url_sub_title(String custom_url_sub_title) {
        this.custom_url_sub_title = custom_url_sub_title;
    }

    public String getPromotion_url_name() {
        return promotion_url_name;
    }

    public void setPromotion_url_name(String promotion_url_name) {
        this.promotion_url_name = promotion_url_name;
    }

    public String getPromotion_url() {
        return promotion_url;
    }

    public void setPromotion_url(String promotion_url) {
        this.promotion_url = promotion_url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
