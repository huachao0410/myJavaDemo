package cn.com.bmsoft.weixin.bean;

/**
 * 卡券对象
 * Created by daniel on 2017/5/16.
 */
public class Card {

    private String card_type;

    private CardMember member_card;

    private CardGenral genral_card;

    public CardMember getMember_card() {
        return member_card;
    }

    public void setMember_card(CardMember member_card) {
        this.member_card = member_card;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public CardGenral getGenral_card() {
        return genral_card;
    }

    public void setGenral_card(CardGenral genral_card) {
        this.genral_card = genral_card;
    }
}
