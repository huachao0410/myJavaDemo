package cn.com.bmsoft.weixin.bean;

/**
 *
 * 卡券对象(使用门槛（条件）)
 *
 * Created by daniel on 2017/5/16.
 */
public class CardUseCondition {

    private String accept_category;
    private String reject_category;
    private Boolean can_use_with_other_discount = true;

    public String getAccept_category() {
        return accept_category;
    }

    public void setAccept_category(String accept_category) {
        this.accept_category = accept_category;
    }

    public boolean isCan_use_with_other_discount() {
        return can_use_with_other_discount;
    }

    public void setCan_use_with_other_discount(boolean can_use_with_other_discount) {
        this.can_use_with_other_discount = can_use_with_other_discount;
    }

    public String getReject_category() {
        return reject_category;
    }

    public void setReject_category(String reject_category) {
        this.reject_category = reject_category;
    }
}
