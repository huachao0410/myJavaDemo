package cn.com.bmsoft.weixin.bean;

/**
 * 卡券对象(商品信息)
 * Created by daniel on 2017/5/16.
 */
public class CardSku {

    private Integer quantity;//卡券库存的数量，不支持填写0，上限为100000000
    private Integer total_quantity;//卡券全部库存的数量，上限为100000000

    public Integer getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(Integer total_quantity) {
        this.total_quantity = total_quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
