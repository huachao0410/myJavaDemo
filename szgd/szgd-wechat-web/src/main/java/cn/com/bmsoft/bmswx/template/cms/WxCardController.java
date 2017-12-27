package cn.com.bmsoft.bmswx.template.cms;

import cn.com.bmsoft.base.common.response.ResponseBean;
import cn.com.bmsoft.base.common.response.ResponseCode;
import cn.com.bmsoft.base.common.web.RequestUtil;
import cn.com.bmsoft.weixin.bean.*;
import cn.com.bmsoft.weixin.service.ICardService;
import cn.com.bmsoft.weixin.service.ICommonService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gzh on 2017/9/6.
 * 微信卡券
 */
@Controller("weixin.card")
@RequestMapping("/weixin/card")
public class WxCardController {
	@Value("${wx_appid}")
	private String appid;

	@Value("${wx_appsecret}")
	private String appsecret;

	@Autowired
	private ICommonService commonService;

	@Autowired
	private ICardService cardService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 创建卡券
	 *
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ResponseBean<String> create(HttpServletRequest request) {
		ResponseBean bean = new ResponseBean();
		try {
			String access_token = this.commonService.getAccessToken(appid, appsecret);

			String card_type = "MEMBER_CARD";
			String background_pic_url = "http://mmbiz.qpic.cn/mmbiz_jpg/dSaibo7FhW3xBySoq0bF4BxVvSF0uQvciaz7AfOKwBT33WaVKnF88wAoQoPvibSGgicicBIq9Om8mAj5J5ibUhXgwOFg/0";
			String logo_url = "http://szga.oss-cn-shenzhen.aliyuncs.com/szga_logo2.png";
			String brand_name = "roly";
			String code_type = "CODE_TYPE_TEXT";
			String title = "测试";
			String color = "Color030";
			String center_title = "个人证照详情";
			String center_url = "http://www.baidu.com";
			String custom_url_name = "测试入口1";
			String custom_url = "http://www.baidu.com ";
			String notice = "使用时向服务员出示此券";
			String description = "个人测试详情查看入口";
			String type = "DATE_TYPE_PERMANENT";
			int quantity = 100000000;
			int get_limit = 1000;
			boolean use_custom_code = false;
			boolean can_give_friend = false;
			boolean need_push_on_view = true;
			boolean supply_bonus = false;
			boolean supply_balance = false;
			String prerogative = "查看个人测试详情";
			boolean auto_activate = true;

			CardSku cardSku = new CardSku();
			cardSku.setQuantity(quantity);

			CardDateInfo cardDateInfo = new CardDateInfo();
			cardDateInfo.setType(type);

			CardBaseInfo cardBaseInfo = new CardBaseInfo();
			cardBaseInfo.setLogo_url(logo_url);
			cardBaseInfo.setBrand_name(brand_name);
			cardBaseInfo.setCode_type(code_type);
			cardBaseInfo.setTitle(title);
			cardBaseInfo.setColor(color);
			cardBaseInfo.setCenter_title(center_title);
			cardBaseInfo.setCenter_url(center_url);
			cardBaseInfo.setCustom_url_name(custom_url_name);
			cardBaseInfo.setCustom_url(custom_url);
			cardBaseInfo.setNotice(notice);
			cardBaseInfo.setDescription(description);
			cardBaseInfo.setDate_info(cardDateInfo);
			cardBaseInfo.setSku(cardSku);
			cardBaseInfo.setGet_limit(get_limit);
			cardBaseInfo.setUse_custom_code(use_custom_code);
			cardBaseInfo.setCan_give_friend(can_give_friend);
			cardBaseInfo.setNeed_push_on_view(need_push_on_view);

			CardMember cardMember = new CardMember();
			cardMember.setBackground_pic_url(background_pic_url);
			cardMember.setBase_info(cardBaseInfo);
			cardMember.setSupply_bonus(supply_bonus);
			cardMember.setSupply_balance(supply_balance);
			cardMember.setPrerogative(prerogative);
			cardMember.setAuto_activate(auto_activate);

			Card card = new Card();
			card.setCard_type(card_type);
			card.setMember_card(cardMember);

			String cardId = this.cardService.create(access_token, card);

			bean.getDatas().add(cardId);

		} catch (Exception e) {
			e.printStackTrace();
			bean.setCode(ResponseCode.FAILED_500);
		}
		return bean;
	}

	/**
	 * 核销票券
	 *
	 * @param code
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/consume", method = RequestMethod.GET)
	public ResponseBean<String> consume(@RequestParam(value = "code") String code, HttpServletRequest request) {
		ResponseBean bean = new ResponseBean();
		try {
			Map<String, Object> queryParams = RequestUtil.asMap(request, false);

			String access_token = this.commonService.getAccessToken(appid, appsecret);

			Object object = queryParams.get("cardId");
			String cardId = "";
			if (object !=null) {
			  cardId = queryParams.get("cardId").toString();
			}

			String codeResult = this.cardService.getCode(access_token, code, cardId, true);
			JSONObject jsonObject = JSON.parseObject(codeResult);
			if (jsonObject!=null && jsonObject.get("errcode") != null && jsonObject.getInteger("errcode") == 0
					&& jsonObject.getString("errmsg").equals("ok")) {
				//卡状态正常
				String user_card_status = jsonObject.getString("user_card_status");
				if (user_card_status.equals("NORMAL") && jsonObject.getBoolean("can_consume")) {
                    //核销
					String result = this.cardService.consume(access_token, code,"");
                    JSONObject consumeObject = JSON.parseObject(result);
                    if (consumeObject !=null &&  consumeObject.get("errcode") != null && consumeObject.getInteger("errcode") == 0
							&& consumeObject.getString("errmsg").equals("ok")) {
                    	bean.setErrorMessage("核销成功");
					}else {
						bean.setCode(ResponseCode.FAILED_400);
						bean.setErrorMessage(result);
					}

				}else {
					bean.setCode(ResponseCode.FAILED_400);
					bean.setErrorMessage("核销失败");
				}
			}else {
				bean.setCode(ResponseCode.FAILED_400);
				bean.setErrorMessage(codeResult);
			}

		} catch (Exception e) {
			e.printStackTrace();
			bean.setCode(ResponseCode.FAILED_500);
		}
		return bean;
	}

	/**
	 * 解码code
	 *
	 * @param encrypt_code
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/decrypt_code", method = RequestMethod.GET)
	public ResponseBean<Map<String, Object>> decrypt_code(
			@RequestParam(value = "encrypt_code") String encrypt_code, HttpServletRequest request) {
		ResponseBean bean = new ResponseBean();
		try {

			String access_token = this.commonService.getAccessToken(appid, appsecret);
			String result = this.cardService.decryptCode(access_token, encrypt_code);
			JSONObject jsonObject = JSONObject.parseObject(result);
			if (jsonObject.get("errcode") != null
					&& jsonObject.getInteger("errcode") == 0
					&& jsonObject.getString("errmsg").equals("ok")) {

				String code = jsonObject.getString("code");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("code", code);
				bean.getDatas().add(map);
			} else {
				bean.setCode(ResponseCode.FAILED_400);
				bean.setErrorMessage(result);
			}


		} catch (Exception e) {
			logger.info(e.getMessage());
			bean.setCode(ResponseCode.FAILED_500);
		}
		return bean;
	}

}
