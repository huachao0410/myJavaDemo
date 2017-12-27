package cn.com.bmsoft.bmswx.template.cms;

import cn.com.bmsoft.base.common.response.ResponseBean;
import cn.com.bmsoft.base.common.response.ResponseCode;
import cn.com.bmsoft.weixin.service.IOauthService;
import cn.com.bmsoft.weixin.service.IOrderService;
import cn.com.bmsoft.weixin.util.Tools;
import cn.com.bmsoft.weixin.util.WXPayConstants;
import cn.com.bmsoft.weixin.util.WXPayUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Created by gzh on 2017/8/21.
 * 微信支付
 */
@Controller("weixin.pay")
@RequestMapping("/weixin/pay")
public class WeixinPayController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Value("${wx_appid}")
	private String appid;

	@Value("${wx_appsecret}")
	private String appsecret;

	@Value("${wx_mch_id}")
	private String mch_id;

	@Value("${wx_key}")
	private String key;

	@Value("${wx_notify_url}")
	private String notify_url;


	@Autowired
	private IOauthService oauthService;

	@Autowired
	private IOrderService orderService;

	/**
	 * 公众号预支付
	 * 发起统一下单api
	 *
	 * @param code
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/wxprepay", method = RequestMethod.GET)
	public ResponseBean<Map<String, Object>> wxprepay(@RequestParam(value = "code") String code, HttpServletRequest request) {
		ResponseBean bean = new ResponseBean();
		try {

			String nonce_str = WXPayUtil.getNonceStr();//随机字符串
			String body = "test";
			String out_trade_no = WXPayUtil.getTradeNo();//商户订单号
			String spbill_create_ip = "10.195.244.226";//终端IP
			String trade_type = "JSAPI";//交易类型
			int total_fee = 1;//订单金额
			String openid = getUserOpenid(code);
			SortedMap<String, String> map = new TreeMap<String, String>();
			map.put("appid", appid);
			map.put("mch_id", mch_id);
			map.put("nonce_str", nonce_str);
			map.put("body", body);
			map.put("out_trade_no", out_trade_no);
			map.put("spbill_create_ip", spbill_create_ip);
			map.put("notify_url", notify_url);
			map.put("trade_type", trade_type);
			map.put("total_fee", String.valueOf(total_fee));
			map.put("openid", openid);
			String sign = WXPayUtil.generateSignature(map, key, WXPayConstants.SignType.MD5);
			map.put("sign", sign);
			String params = WXPayUtil.mapToXml(map);

			System.out.println("params:" + params);

			String result = this.orderService.unifiedOrder(params);
			System.out.println("result:" + result);

			Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
			String return_code = resultMap.get("return_code");
			String result_code = resultMap.get("result_code");
			if (return_code != null && result_code != null
					&& return_code.equals("SUCCESS")
					&& return_code.equals(result_code)) {

				String prepay_id = resultMap.get("prepay_id");
				//签名的参数有appId, timeStamp, nonceStr, package, signType
				Map<String, String> payMap = new HashMap<String, String>();
				payMap.put("appId", appid);
				payMap.put("package", "prepay_id=" + prepay_id);
				payMap.put("nonceStr", WXPayUtil.getNonceStr());
				payMap.put("timeStamp", String.valueOf(WXPayUtil.getCurrentTimestamp()));
				payMap.put("signType", "MD5");
				String paySign = WXPayUtil.generateSignature(payMap, key, WXPayConstants.SignType.MD5);
				payMap.put("paySign", paySign);

				bean.getDatas().add(payMap);
			} else {
				bean.setCode(ResponseCode.FAILED_400);
				bean.setErrorMessage(result);
			}


		} catch (Exception e) {
			logger.info(e.getMessage());
			bean.setCode(ResponseCode.FAILED_500);
			bean.setErrorMessage(e.getMessage());
		}
		return bean;
	}


	/**
	 * h5支付
	 * 发起统一下单api
	 *
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/webprepay", method = RequestMethod.GET)
	public ResponseBean<Map<String, Object>> webprepay(HttpServletRequest request) {
		ResponseBean bean = new ResponseBean();
		try {

			String nonce_str = WXPayUtil.getNonceStr();//随机字符串
			String body = "test";
			String out_trade_no = WXPayUtil.getTradeNo();//商户订单号
			String spbill_create_ip = "10.195.244.226";//终端IP
			String trade_type = "MWEB";//H5支付类型
			int total_fee = 1;//订单金额
			SortedMap<String, String> map = new TreeMap<String, String>();
			map.put("appid", appid);
			map.put("mch_id", mch_id);
			map.put("nonce_str", nonce_str);
			map.put("body", body);
			map.put("out_trade_no", out_trade_no);
			map.put("spbill_create_ip", spbill_create_ip);
			map.put("notify_url", notify_url);
			map.put("trade_type", trade_type);
			map.put("total_fee", String.valueOf(total_fee));
			String sign = WXPayUtil.generateSignature(map, key, WXPayConstants.SignType.MD5);
			map.put("sign", sign);
			String params = WXPayUtil.mapToXml(map);

			System.out.println("params:" + params);

			String result = this.orderService.unifiedOrder(params);
			System.out.println("result:" + result);

			Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
			String return_code = resultMap.get("return_code");
			String result_code = resultMap.get("result_code");
			if (return_code != null && result_code != null
					&& return_code.equals("SUCCESS")
					&& return_code.equals(result_code)) {

				String mweb_url = resultMap.get("mweb_url");
				Map<String, String> payMap = new HashMap<String, String>();
				payMap.put("mweb_url", mweb_url);
				bean.getDatas().add(payMap);
			} else {
				bean.setCode(ResponseCode.FAILED_400);
				bean.setErrorMessage(result);
			}


		} catch (Exception e) {
			logger.info(e.getMessage());
			bean.setCode(ResponseCode.FAILED_500);
			bean.setErrorMessage(e.getMessage());
		}
		return bean;
	}


	/**
	 * 扫码支付
	 * 发起统一下单api
	 *
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/nativeprepay", method = RequestMethod.GET)
	public ResponseBean<Map<String, Object>> nativeprepay(HttpServletRequest request) {
		ResponseBean bean = new ResponseBean();
		try {

			String nonce_str = WXPayUtil.getNonceStr();//随机字符串
			String body = "test";
			String out_trade_no = WXPayUtil.getTradeNo();//商户订单号
			String spbill_create_ip = "10.195.244.226";//终端IP
			String trade_type = "NATIVE";//H5支付类型
			int total_fee = 1;//订单金额
			SortedMap<String, String> map = new TreeMap<String, String>();
			map.put("appid", appid);
			map.put("mch_id", mch_id);
			map.put("nonce_str", nonce_str);
			map.put("body", body);
			map.put("out_trade_no", out_trade_no);
			map.put("spbill_create_ip", spbill_create_ip);
			map.put("notify_url", notify_url);
			map.put("trade_type", trade_type);
			map.put("total_fee", String.valueOf(total_fee));
			String sign = WXPayUtil.generateSignature(map, key, WXPayConstants.SignType.MD5);
			map.put("sign", sign);
			String params = WXPayUtil.mapToXml(map);

			System.out.println("params:" + params);

			String result = this.orderService.unifiedOrder(params);
			System.out.println("result:" + result);

			Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
			String return_code = resultMap.get("return_code");
			String result_code = resultMap.get("result_code");
			if (return_code != null && result_code != null
					&& return_code.equals("SUCCESS")
					&& return_code.equals(result_code)) {

				//支付金额转换由分转为元
				float num = (float) total_fee / 100;
				DecimalFormat df = new DecimalFormat("0.00");//格式化小数
				String fee = df.format(num);//返回的是String类型
				// 返回数据处理
				String code_url = resultMap.get("code_url");
				Map<String, String> payMap = new HashMap<String, String>();
				payMap.put("code_url", code_url);
				payMap.put("out_trade_no", out_trade_no);
				payMap.put("total_fee", fee);
				bean.getDatas().add(payMap);


			} else {
				bean.setCode(ResponseCode.FAILED_400);
				bean.setErrorMessage(result);
			}


		} catch (Exception e) {
			logger.info(e.getMessage());
			bean.setCode(ResponseCode.FAILED_500);
			bean.setErrorMessage(e.getMessage());
		}
		return bean;
	}

	/**
	 * 支付通知相应处理
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/paynotify", method = RequestMethod.POST)
	public String paynotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resXml = "fail";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		ServletInputStream in = request.getInputStream();
		String xmlMsg = Tools.inputStream2String(in);

		logger.info(xmlMsg);
		if (!TextUtils.isEmpty(xmlMsg)) {
			Map<String, String> map = WXPayUtil.xmlToMap(xmlMsg);
			String return_code = map.get("return_code");
			String return_msg = map.get("return_msg");

			if (return_code.equals("SUCCESS") && TextUtils.isEmpty(return_msg)) {
				return_msg = "OK";
			}
			map = new HashMap<String, String>();
			map.put("return_code", return_code);
			map.put("return_msg", return_msg);

			// 响应xml
			resXml = WXPayUtil.mapToXml(map);
			logger.info("resXml:" + resXml);

		}

		return resXml;

	}

	/**
	 * 订单查询
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/orderquery", method = RequestMethod.GET)
	public ResponseBean orderquery(@RequestParam(value = "orderType") String orderType, @RequestParam(value = "orderNumber") String orderNumber, HttpServletRequest request) throws Exception {
		ResponseBean bean = new ResponseBean();
		try {
			String nonce_str = WXPayUtil.getNonceStr();//随机字符串
			Map<String, String> map = new HashMap<String, String>();
			map.put("appid", appid);
			map.put("mch_id", mch_id);
			map.put("nonce_str", nonce_str);
			map.put(orderType, orderNumber);
			String sign = WXPayUtil.generateSignature(map, key, WXPayConstants.SignType.MD5);
			map.put("sign", sign);
			String params = WXPayUtil.mapToXml(map);

			System.out.println("params:" + params);

			String result = this.orderService.orderQuery(params);
			System.out.println("result:" + result);

			Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
			String return_code = resultMap.get("return_code");
			String result_code = resultMap.get("result_code");
			String trade_state = resultMap.get("trade_state");
			if (return_code != null && result_code != null && trade_state != null
					&& return_code.equals("SUCCESS")
					&& return_code.equals("SUCCESS")
					&& trade_state.equals("SUCCESS")) {

				String total_fee = resultMap.get("total_fee");
				String time_end = resultMap.get("time_end");
				String bank_type = resultMap.get("bank_type");
				//支付金额转换由分转为元
				float num = (float) Integer.parseInt(total_fee) / 100;
				DecimalFormat df = new DecimalFormat("0.00");//格式化小数
				String fee = "¥ " + df.format(num);//返回的是String类型/100;

				if (bank_type.equals("CFT")) {
					resultMap.put("bank_type", "零钱");
				}
				resultMap.put("trade_state", "成功");
				resultMap.put("total_fee", fee);
				resultMap.put("time_end", WXPayUtil.getFormatDate(time_end));
				bean.getDatas().add(resultMap);

			} else {
				bean.setCode(ResponseCode.FAILED_400);
				bean.setErrorMessage(result);
			}

		} catch (Exception e) {
			bean.setCode(ResponseCode.FAILED_500);
		}

		return bean;
	}


	/**
	 * 申请退款
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/payrefund", method = RequestMethod.GET)
	public ResponseBean payrefund(@RequestParam(value = "out_trade_no") String out_trade_no, HttpServletRequest request) throws Exception {
		ResponseBean bean = new ResponseBean();
		try {
			String nonce_str = WXPayUtil.getNonceStr();//随机字符串
			Map<String, String> map = new HashMap<String, String>();
			map.put("appid", appid);
			map.put("mch_id", mch_id);
			map.put("nonce_str", nonce_str);
			map.put("out_trade_no", out_trade_no);
			map.put("out_refund_no", WXPayUtil.getTradeNo());
			map.put("total_fee", "1");
			map.put("refund_fee", "1");
			String sign = WXPayUtil.generateSignature(map, key, WXPayConstants.SignType.MD5);
			map.put("sign", sign);
			String params = WXPayUtil.mapToXml(map);

			System.out.println("payrefund:" + params);

			getCert();
			String result = this.orderService.payRefund(params);
			System.out.println("result:" + result);

			Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
			String return_code = resultMap.get("return_code");
			if (return_code != null && return_code.equals("SUCCESS")) {
				//退款成功
				bean.setErrorMessage("退款成功");
			} else {
				bean.setCode(ResponseCode.FAILED_400);
				bean.setErrorMessage(result);
			}

		} catch (Exception e) {
			bean.setCode(ResponseCode.FAILED_500);
		}

		return bean;
	}

	/**
	 * 加载证书
	 *
	 * @throws Exception
	 */
	private void getCert() throws Exception {
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		FileInputStream instream = new FileInputStream(new File("D:/10016225.p12"));
		try {
			keyStore.load(instream, "10016225".toCharArray());
		} finally {
			instream.close();
		}

		// Trust own CA and all self-signed certs
		SSLContext sslcontext = SSLContexts.custom()
				.loadKeyMaterial(keyStore, "10016225".toCharArray())
				.build();
		// Allow TLSv1 protocol only
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslcontext,
				new String[]{"TLSv1"},
				null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom()
				.setSSLSocketFactory(sslsf)
				.build();
	}

	/**
	 * 获取用户微信开发号
	 *
	 * @param code
	 * @return
	 */
	private String getUserOpenid(String code) {
		try {
			String token = this.oauthService.getToken(appid, appsecret, code);
			JSONObject obj = JSONObject.parseObject(token);
			Object openid = obj.get("openid");
			if (openid != null) {
				return openid + "";
			}
		} catch (Exception e) {
		}
		return "";
	}


}
