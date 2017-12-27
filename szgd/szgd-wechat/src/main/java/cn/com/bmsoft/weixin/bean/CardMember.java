package cn.com.bmsoft.weixin.bean;

/**
 * 卡券对象（会员卡）
 * Created by daniel on 2017/5/16.
 */
public class CardMember {

    private String background_pic_url;//商家自定义会员卡背景图
    private CardBaseInfo base_info;//基本的卡券数据
    private CardAdvancedInfo advanced_info;
    private Boolean supply_bonus;//显示积分，填写true或false，如填写true，积分相关字段均为必填。
    private String bonus_url;//设置跳转外链查看积分详情。
    private String balance_url;//设置跳转外链查看余额详情。
    private Boolean supply_balance;//是否支持储值，填写true或false。
    private String prerogative;//会员卡特权说明
    private Boolean auto_activate = true;//设置为true时用户领取会员卡后系统自动将其激活，无需调用激活接口
    private Boolean wx_activate;//设置为true时会员卡支持一键开卡，不允许同时传入activate_url字段，否则设置wx_activate失效。
    private CardCustomField custom_field1;
    private CardCustomField custom_field2;
    private CardCustomField custom_field3;
    private String activate_url;//激活会员卡的url
    private CardCustomCell custom_cell1;//自定义会员信息类目，会员卡激活后显示
    private CardBonusRule bonus_rule;//积分规则。
    private Integer discount;//折扣，该会员卡享受的折扣优惠,填10就是九折。

    private String bonus_cleared;//积分清零规则。
    private String bonus_rules;//积分规则
    private String balance_rules;//储值说明


    public String getActivate_url() {
        return activate_url;
    }

    public void setActivate_url(String activate_url) {
        this.activate_url = activate_url;
    }

    public CardAdvancedInfo getAdvanced_info() {
        return advanced_info;
    }

    public void setAdvanced_info(CardAdvancedInfo advanced_info) {
        this.advanced_info = advanced_info;
    }

    public Boolean isAuto_activate() {
        return auto_activate;
    }

    public void setAuto_activate(Boolean auto_activate) {
        this.auto_activate = auto_activate;
    }

    public String getBackground_pic_url() {
        return background_pic_url;
    }

    public void setBackground_pic_url(String background_pic_url) {
        this.background_pic_url = background_pic_url;
    }

    public CardBaseInfo getBase_info() {
        return base_info;
    }

    public void setBase_info(CardBaseInfo base_info) {
        this.base_info = base_info;
    }

    public CardBonusRule getBonus_rule() {
        return bonus_rule;
    }

    public void setBonus_rule(CardBonusRule bonus_rule) {
        this.bonus_rule = bonus_rule;
    }

    public CardCustomCell getCustom_cell1() {
        return custom_cell1;
    }

    public void setCustom_cell1(CardCustomCell custom_cell1) {
        this.custom_cell1 = custom_cell1;
    }

    public CardCustomField getCustom_field1() {
        return custom_field1;
    }

    public void setCustom_field1(CardCustomField custom_field1) {
        this.custom_field1 = custom_field1;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getPrerogative() {
        return prerogative;
    }

    public void setPrerogative(String prerogative) {
        this.prerogative = prerogative;
    }

    public Boolean isSupply_balance() {
        return supply_balance;
    }

    public void setSupply_balance(Boolean supply_balance) {
        this.supply_balance = supply_balance;
    }

    public Boolean isSupply_bonus() {
        return supply_bonus;
    }

    public void setSupply_bonus(Boolean supply_bonus) {
        this.supply_bonus = supply_bonus;
    }
}
