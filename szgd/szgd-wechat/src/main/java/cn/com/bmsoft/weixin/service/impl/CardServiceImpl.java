package cn.com.bmsoft.weixin.service.impl;

import cn.com.bmsoft.weixin.bean.Card;
import cn.com.bmsoft.weixin.bean.CardWhiteList;
import cn.com.bmsoft.weixin.service.ICardService;
import cn.com.bmsoft.weixin.util.HttpKit;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * 微信公共服务
 * 
 * @author daniel
 *
 */
@Service
public class CardServiceImpl implements ICardService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 上传卡券所需图片
    private static final String UPLOAD_IMG_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=";
    // 创建卡券
	private static final String CREATE_CARD_URL = "https://api.weixin.qq.com/card/create?access_token=";
    // 修改卡券
    private static final String UPDATE_CARD_URL = "https://api.weixin.qq.com/card/update?access_token=";
    // 删除卡券
    private static final String DELETE_CARD_URL = "https://api.weixin.qq.com/card/delete?access_token=";
    // 设置测试白名单
    private static final String WHITE_LIST_URL = "https://api.weixin.qq.com/card/testwhitelist/set?access_token=";
    // 查询code
    private static final String GET_CODE_URL = "https://api.weixin.qq.com/card/code/get?access_token=";
    // 卡券详细信息接口
    private static final String UPDATE_CODE_URL = "https://api.weixin.qq.com/card/code/update?access_token=";
    // 核销Code接口
    private static final String CONSUME_CODE_URL = "https://api.weixin.qq.com/card/code/consume?access_token=";
    // 查询卡券信息接口
    private static final String GET_URL = "https://api.weixin.qq.com/card/get?access_token=";
    // 查询用户卡列表接口
    private static final String GET_CARD_LIST_URL = "https://api.weixin.qq.com/card/user/getcardlist?access_token=";
    // 批量查询卡券列表接口
    private static final String BATCH_GET_URL = "https://api.weixin.qq.com/card/batchget?access_token=";
    //解码code
    private static final String DECRYPT_CODE_URL = "https://api.weixin.qq.com/card/code/decrypt?access_token=";

    @Override
    public String uploadImg(String accessToken, File file) throws Exception {
        String jsonStr = HttpKit.upload(UPLOAD_IMG_URL.concat(accessToken), file);
        logger.info("card:uploadimg:" + jsonStr);
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        return jsonObject.get("url").toString();
    }

    @Override
    public String get(String accessToken,String cardId) throws Exception {
        String strJson = "{\"card_id\":\"" + cardId +"\"}";
        String jsonStr = HttpKit.post(GET_URL.concat(accessToken), strJson);
        logger.info("card:get:" + jsonStr);
        return jsonStr;
    }

    @Override
    public String getCode(String accessToken, String cardCode, String cardId, boolean checkConsume) throws Exception {
        String strJson = "{\"code\":\"" + cardCode +"\",\"check_consume\":" + checkConsume;
        if(cardId != null) {
            strJson += ",\"card_id\":\"" + cardId + "\"";
        }
        strJson  += "}";
        String jsonStr = HttpKit.post(GET_CODE_URL.concat(accessToken), strJson);
        logger.info("card:get:" + jsonStr);
        return jsonStr;
    }

    @Override
    public String update(String accessToken, String cardCode, String cardId, String newCardCode) throws Exception {
        String strJson = "{\"code\":\"" + cardCode +"\",\"new_code\":" + newCardCode;
        if(cardId != null) {
            strJson += ",\"card_id\":\"" + cardId + "\"";
        }
        strJson  += "}";
        String jsonStr = HttpKit.post(UPDATE_CODE_URL.concat(accessToken), strJson);
        logger.info("card:get:" + jsonStr);
        return jsonStr;
    }

    @Override
    public String consume(String accessToken, String cardCode, String cardId) throws Exception {
        String strJson = "{\"code\":\"" + cardCode +"\"";
        if(!TextUtils.isEmpty(cardId)) {
            strJson += ",\"card_id\":\"" + cardId + "\"";
        }
        strJson  += "}";
        logger.info(strJson);
        String jsonStr = HttpKit.post(CONSUME_CODE_URL.concat(accessToken), strJson);
        logger.info("card:consume:" + jsonStr);
        return jsonStr;
    }

    @Override
    public String getCardList(String accessToken, String openid, String cardId) throws Exception {
        String strJson = "{\"openid\":\"" + openid +"\"";
        if(cardId != null) {
            strJson += ",\"card_id\":\"" + cardId + "\"";
        }
        strJson  += "}";
        String jsonStr = HttpKit.post(GET_CARD_LIST_URL.concat(accessToken), strJson);
        logger.info("card:get:" + jsonStr);
        return jsonStr;
    }

    @Override
    public String create(String accessToken, Card card) throws Exception {
        String strJson = "{\"card\":" + JSONObject.toJSONString(card) + "}";
        String  jsonStr = HttpKit.post(CREATE_CARD_URL.concat(accessToken), strJson);
        logger.info("card:create:" + jsonStr);
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        return jsonObject.get("card_id").toString();
    }

    @Override
    public String update(String accessToken, Card card) throws Exception {
        String strJson = "{\"card\":" + JSONObject.toJSONString(card) + "}";
        String  jsonStr = HttpKit.post(UPDATE_CARD_URL.concat(accessToken), strJson);
        logger.info("card:update:" + jsonStr);
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        return jsonObject.get("card_id").toString();
    }

    @Override
    public String delete(String accessToken, String cardId) throws Exception {
        String strJson = "{\"card_id\":\"" + cardId +"\"}";
        String jsonStr = HttpKit.post(DELETE_CARD_URL.concat(accessToken), strJson);
        logger.info("card:delete:" + jsonStr);
        return jsonStr;
    }

    @Override
    public String testWhiteList(String accessToken, CardWhiteList whiteList) throws Exception {
        String  jsonStr = HttpKit.post(WHITE_LIST_URL.concat(accessToken), JSONObject.toJSONString(whiteList));
        logger.info("card:testwhitelist:" + jsonStr);
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        return jsonObject.get("errmsg").toString();
    }

    @Override
    public String batchGet(String accessToken, int offset, int count, String[] statusList) throws Exception {
        String strJson = "{\"offset\":" + offset +",\"count\":" + count;
        if(statusList != null) {
            strJson += ",\"status_list\":" + JSONObject.toJSONString(statusList) + "";
        }
        strJson  += "}";
        String jsonStr = HttpKit.post(BATCH_GET_URL.concat(accessToken), strJson);
        logger.info("card:batchGet:/n requst:" + strJson + "/n response:" + jsonStr);
        return jsonStr;
    }

    @Override
    public String decryptCode(String accessToken, String encrypt_code) throws Exception {
        String postParams = "{\"encrypt_code\":\""+encrypt_code+"\"}";
        String jsonStr = HttpKit.post(DECRYPT_CODE_URL.concat(accessToken), postParams);
        logger.info("card:decryptCode:/n request:" + postParams + "/n response:" + jsonStr);
        return jsonStr;
    }
}
