package cn.com.bmsoft.weixin.service.test;

import cn.com.bmsoft.weixin.bean.*;
import cn.com.bmsoft.weixin.service.ICardService;
import cn.com.bmsoft.weixin.service.ICommonService;
import cn.com.bmsoft.weixin.service.IUserService;
import cn.com.bmsoft.weixin.service.impl.CardServiceImpl;
import cn.com.bmsoft.weixin.service.impl.CommonServiceImpl;
import cn.com.bmsoft.weixin.service.impl.UserServiceImpl;
import cn.com.bmsoft.weixin.util.HttpKit;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.List;

/**
 * Created by daniel on 2017/5/17.
 */
public class CardServiceTestCase {

    private ICommonService commonService = new CommonServiceImpl();

    private ICardService cardService = new CardServiceImpl();

    private IUserService userService = new UserServiceImpl();

    // 深圳测试号
//    private static String appid = "wx0fd5e8faf6f81226";
//    private static String appsecret = "feb88a59e1ef9d6e92276241a409ccab";

//    private static String appid = "wx86908dcd4a4d5ee5";
//    private static String appsecret = "ac865c3632e7dc96621ac7101232be52";

    private static String appid = "wx08caec7ef81aab5f";
    private static String appsecret = "0ff9e0f6a1fef32e75e28787bdeb1e48";


    private static String getAccessTokenUrl = "http://msjwwx.szga.gov.cn/bmswx/weixin/component/getAccessToken?accountId=3";

    @Test
    public void testWhiteList() {
        try {
            String accessToken = HttpKit.get(getAccessTokenUrl);
            accessToken = commonService.getAccessToken(appid, appsecret);
            CardWhiteList whiteList = new CardWhiteList();
            whiteList.addOpenid("oIH7Uw0cKNWBnGM0yQCU1PGEWJlQ");
            whiteList.addOpenid("oIH7Uw-WKAubS_MDIHL5O5s_iVJw");
            String result = cardService.testWhiteList(accessToken, whiteList);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getCardList() {
        try {
            String accessToken = HttpKit.get(getAccessTokenUrl);
            String result = cardService.getCardList(accessToken, "oIH7Uw0ge8w4vba8OUYGq4nBBO7I", null);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteCard() {
        try {
            String[] ids = {
                    "p3rQ-w5khajAl63wRBLbE4mItvlA",
                    "p3rQ-w54Eq6y-Jc2twFSXyB-Y-0Y",
                    "p3rQ-w50Cbro1xAEM-4VVNaR2ANM",
                    "p3rQ-w_abvthwu8mj41omBVSXR9E",
                    "p3rQ-w4KvaL3EQ1LPhnEbgMLeakU",
                    "p3rQ-w8jfwzhWXZnnta2kyX5lqO4",
                    "p3rQ-wzi_SKfczUboRpnh21v7Wys",
                    "p3rQ-wz6Skrd8WnWb3yLtS9xfyyg",
                    "p3rQ-wzt-PSZcrgTOoJLEWek4sqc",
                    "p3rQ-w6-p8bVktQ98nKJjvfwhZy0"
            };
            String accessToken = HttpKit.get(getAccessTokenUrl);
            accessToken = commonService.getAccessToken(appid, appsecret);
            for (String id : ids) {
                String result = cardService.delete(accessToken, id);
                System.out.println(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void batchGet() {
        try {
            String accessToken = HttpKit.get(getAccessTokenUrl + "&time=" + System.currentTimeMillis());
//            accessToken = commonService.getAccessToken(appid, appsecret);
            String[] list = {"CARD_STATUS_NOT_VERIFY"};
            String result = cardService.batchGet(accessToken, 0, 7, list);

            List<String> userOpenids = userService.getUserOpenids(accessToken, null);

            JSONObject jsonObject = JSONObject.parseObject(result);
            JSONArray card_id_list = jsonObject.getJSONArray("card_id_list");
            Integer total = jsonObject.getInteger("total_num");
            Object[] cardIds = card_id_list.toArray();
            System.out.println("共有：" + total);
            for (int i = 0; i < cardIds.length; i++) {
                String cardId = cardIds[i].toString();
                System.out.println((i+1) + ":" + cardId);
//                result = this.cardService.get(accessToken, cardId);
//                System.out.println(result);
                for(String openid : userOpenids) {
                    result = this.cardService.getCardList(accessToken, openid, cardId);
                    jsonObject = JSONObject.parseObject(result);
                    JSONArray card_list = jsonObject.getJSONArray("card_list");
                    System.out.println(card_list.size() + ":" + result);
                }
//                this.cardService.delete(accessToken, cardId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getCode() {
        try {
            //050245908927
            String accessToken = HttpKit.get(getAccessTokenUrl);
            String result = this.cardService.getCode(accessToken, "050245908927", null, true);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateCode() {
        try {
            String accessToken = HttpKit.get(getAccessTokenUrl);
            accessToken = commonService.getAccessToken(appid, appsecret);
            String result = this.cardService.update(accessToken, "025763662766", "pqN7BtwRSk8M3jJimOpG_Zd8sNog", "112233445566");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void consumeCode() {
        try {
            String accessToken = HttpKit.get(getAccessTokenUrl);
            accessToken = commonService.getAccessToken(appid, appsecret);
            String result = this.cardService.consume(accessToken, "025763662766", null);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void createMemberCard() {
        try {
            String accessToken = HttpKit.get(getAccessTokenUrl);
//            accessToken = commonService.getAccessToken(appid, appsecret);

            Card card = new Card();
            card.setCard_type("MEMBER_CARD");
            CardMember memberCard = new CardMember();

            memberCard.setBackground_pic_url("http://mmbiz.qpic.cn/mmbiz_jpg/hWZMm76Zd6PrAv5JAVCMBs7KDEQfhdPzScygQ6aX1ulhm9ibXjxCCTCfCdu5yZVgcBvBwXYpskpMx2YibsibUtUoQ/0");
            memberCard.setPrerogative("可查看个人证照详情");
            memberCard.setDiscount(0);
            memberCard.setSupply_bonus(false);
            memberCard.setSupply_balance(false);
            memberCard.setActivate_url("http://www.qq.com");
            memberCard.setAuto_activate(true);

            CardBaseInfo baseInfo = new CardBaseInfo();
            baseInfo.setLogo_url("http://mmbiz.qpic.cn/mmbiz_jpg/hWZMm76Zd6PrAv5JAVCMBs7KDEQfhdPzRjtb7ScnrkVMfU53eU28IdMJU3HeXj56IZXWyg3D8WSl2SWslK2wdw/0");
            baseInfo.setCode_type("CODE_TYPE_QRCODE");
            baseInfo.setBrand_name("户口1");
            baseInfo.setTitle("户口1会员卡");
            baseInfo.setColor("Color010");
            baseInfo.setNotice("仅仅只是测试");
            baseInfo.setDescription("不可与其他优惠同享");
            baseInfo.setUse_custom_code(false);
            baseInfo.setCan_give_friend(false);
            baseInfo.setNeed_push_on_view(true);

            CardSku sku = new CardSku();
            sku.setQuantity(50000000);
            baseInfo.setSku(sku);

            CardDateInfo dateInfo = new CardDateInfo();
            dateInfo.setType("DATE_TYPE_PERMANENT");
            baseInfo.setDate_info(dateInfo);

            memberCard.setBase_info(baseInfo);
            card.setMember_card(memberCard);

            String cardId = cardService.create(accessToken, card);
            System.out.println("cardId:" + cardId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createGenralCard() {
        try {
            String accessToken = HttpKit.get(getAccessTokenUrl);

            Card card = new Card();
            card.setCard_type("GENRAL_CARD");
            CardGenral genralCard = new CardGenral();

            genralCard.setBackground_pic_url("http://mmbiz.qpic.cn/mmbiz_jpg/hWZMm76Zd6PrAv5JAVCMBs7KDEQfhdPzScygQ6aX1ulhm9ibXjxCCTCfCdu5yZVgcBvBwXYpskpMx2YibsibUtUoQ/0");
            genralCard.setPrerogative("可查看个人证照详情");
            genralCard.setDiscount(0);
            genralCard.setSupply_bonus(false);
            genralCard.setSupply_balance(false);
            genralCard.setActivate_url("http://www.qq.com");
            genralCard.setAuto_activate(true);

            CardBaseInfo baseInfo = new CardBaseInfo();
            baseInfo.setLogo_url("http://mmbiz.qpic.cn/mmbiz_jpg/hWZMm76Zd6PrAv5JAVCMBs7KDEQfhdPzRjtb7ScnrkVMfU53eU28IdMJU3HeXj56IZXWyg3D8WSl2SWslK2wdw/0");
            baseInfo.setCode_type("CODE_TYPE_QRCODE");
            baseInfo.setBrand_name("户口1");
            baseInfo.setTitle("户口1会员卡");
            baseInfo.setColor("Color010");
            baseInfo.setNotice("仅仅只是测试");
            baseInfo.setDescription("不可与其他优惠同享");
            baseInfo.setUse_custom_code(false);
            baseInfo.setCan_give_friend(false);
            baseInfo.setNeed_push_on_view(true);

            CardSku sku = new CardSku();
            sku.setQuantity(50000000);
            baseInfo.setSku(sku);

            CardDateInfo dateInfo = new CardDateInfo();
            dateInfo.setType("DATE_TYPE_PERMANENT");
            baseInfo.setDate_info(dateInfo);

            genralCard.setBase_info(baseInfo);
            card.setGenral_card(genralCard);

            String cardId = cardService.create(accessToken, card);
            System.out.println("cardId:" + cardId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
