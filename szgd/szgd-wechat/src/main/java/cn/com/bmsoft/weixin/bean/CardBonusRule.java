package cn.com.bmsoft.weixin.bean;

/**
 * Created by daniel on 2017/5/16.
 */
public class CardBonusRule {

    private Integer cost_money_unit;//消费金额。以分为单位。
    private Integer increase_bonus;//对应增加的积分。
    private Integer max_increase_bonus;//用户单次可获取的积分上限。
    private Integer init_increase_bonus;//初始设置积分。
    private Integer cost_bonus_unit;//每使用5积分。
    private Integer reduce_money;//抵扣xx元，（这里以分为单位）
    private Integer least_money_to_use_bonus;//抵扣条件，满xx元（这里以分为单位）可用。
    private Integer max_reduce_bonus;//抵扣条件，单笔最多使用xx积分。

    public Integer getCost_bonus_unit() {
        return cost_bonus_unit;
    }

    public void setCost_bonus_unit(Integer cost_bonus_unit) {
        this.cost_bonus_unit = cost_bonus_unit;
    }

    public Integer getCost_money_unit() {
        return cost_money_unit;
    }

    public void setCost_money_unit(Integer cost_money_unit) {
        this.cost_money_unit = cost_money_unit;
    }

    public Integer getIncrease_bonus() {
        return increase_bonus;
    }

    public void setIncrease_bonus(Integer increase_bonus) {
        this.increase_bonus = increase_bonus;
    }

    public Integer getInit_increase_bonus() {
        return init_increase_bonus;
    }

    public void setInit_increase_bonus(Integer init_increase_bonus) {
        this.init_increase_bonus = init_increase_bonus;
    }

    public Integer getLeast_money_to_use_bonus() {
        return least_money_to_use_bonus;
    }

    public void setLeast_money_to_use_bonus(Integer least_money_to_use_bonus) {
        this.least_money_to_use_bonus = least_money_to_use_bonus;
    }

    public Integer getMax_increase_bonus() {
        return max_increase_bonus;
    }

    public void setMax_increase_bonus(Integer max_increase_bonus) {
        this.max_increase_bonus = max_increase_bonus;
    }

    public Integer getMax_reduce_bonus() {
        return max_reduce_bonus;
    }

    public void setMax_reduce_bonus(Integer max_reduce_bonus) {
        this.max_reduce_bonus = max_reduce_bonus;
    }

    public Integer getReduce_money() {
        return reduce_money;
    }

    public void setReduce_money(Integer reduce_money) {
        this.reduce_money = reduce_money;
    }
}
