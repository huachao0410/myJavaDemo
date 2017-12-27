package cn.com.bmsoft.weixin.service;


import cn.com.bmsoft.weixin.bean.Card;
import cn.com.bmsoft.weixin.bean.CardWhiteList;

import java.io.File;

/**
 * 微信卡券服务
 * 
 * @author daniel
 *
 */
public interface ICardService {


   public String uploadImg(String accessToken, File file) throws Exception;

   /**
    * 获取卡信息
    *
    * @param accessToken
    * @param cardId
    * @return
    * @throws Exception
    */
   public String get(String accessToken, String cardId) throws Exception;

   /**
    * 查询code
    *
    * @param accessToken
    * @param cardCode
    * @param cardId
    * @param checkConsume
    * @return
    * @throws Exception
    */
   public String getCode(String accessToken, String cardCode, String cardId, boolean checkConsume) throws Exception;

   /**
    * 更新卡券的卡号
    *
    * @param accessToken
    * @param cardCode
    * @param cardId
    * @param newCardCode
    * @return
    * @throws Exception
    */
   public String update(String accessToken, String cardCode, String cardId, String newCardCode) throws Exception;

   /**
    * 核销卡券
    *
    * @param accessToken
    * @param cardCode
    * @param cardId
    * @return
    * @throws Exception
    */
   public String consume(String accessToken, String cardCode, String cardId) throws Exception;

   /**
    * 获取用户的卡券列表
    * @param accessToken 是
    * @param openid 是
    * @param cardId 否
    * @return
    * @throws Exception
    */
   public String getCardList(String accessToken, String openid, String cardId) throws Exception;

   /**
    * 创建卡券
    * @param accessToken
    * @param card
    * @return
    * @throws Exception
    */
   public String create(String accessToken, Card card) throws Exception;

   /**
    * 修改卡券
    * @param accessToken
    * @param card
    * @return
    * @throws Exception
    */
   public String update(String accessToken, Card card) throws Exception;

   /**
    * 删除卡券
    * @param accessToken
    * @param cardId
    * @return
    * @throws Exception
    */
   public String delete(String accessToken, String cardId) throws Exception;



   /**
    * 设置卡券白名单
    * @param accessToken
    * @param whiteList
    * @return
    * @throws Exception
    */
   public String testWhiteList(String accessToken, CardWhiteList whiteList) throws Exception;

   /**
    * 批量获取卡
    * @param accessToken
    * @param offset 查询卡列表的起始偏移量
    * @param count 需要查询的卡片的数量
    * @param statusList 否，“CARD_STATUS_NOT_VERIFY”,待审核；“CARD_STATUS_VERIFY_FAIL”, 审核失败；“CARD_STATUS_VERIFY_OK”，通过审核；CARD_STATUS_DELETE”，卡券被商户删除；CARD_STATUS_DISPATCH”， 在公众平台投放过的卡券；
    * @return
    * @throws Exception
    */
   public String batchGet(String accessToken, int offset, int count, String[] statusList) throws Exception;


   public String decryptCode(String accessToken, String encrypt_code) throws Exception;

}
