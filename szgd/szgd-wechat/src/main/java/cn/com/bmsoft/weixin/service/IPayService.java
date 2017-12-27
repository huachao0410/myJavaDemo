package cn.com.bmsoft.weixin.service;

import java.util.Map;

public interface IPayService {

	/**
	 * 参与 paySign 签名的字段包括：appid、timestamp、noncestr、package 以及 appkey。 这里
	 * signType 并不参与签名微信的Package参数
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getPackage(Map<String, String> params) throws Exception;

	/**
	 * 构造签名
	 * 
	 * @param params
	 * @param encode
	 * @return
	 * @throws Exception
	 */
	public String createSign(Map<String, String> params, boolean encode) throws Exception;

	/**
	 * 支付签名
	 * 
	 * @param timestamp
	 * @param noncestr
	 * @param packages
	 * @return
	 * @throws Exception
	 */
	public String paySign(String timestamp, String noncestr, String packages) throws Exception;

	/**
	 * 支付回调校验签名
	 * 
	 * @param timestamp
	 * @param noncestr
	 * @param openid
	 * @param issubscribe
	 * @param appsignature
	 * @return
	 * @throws Exception
	 */
	public boolean verifySign(long timestamp, String noncestr, String openid, int issubscribe, String appsignature)
			throws Exception;

	/**
	 * 发货通知
	 * 
	 * @param access_token
	 * @param openid
	 * @param transid
	 * @param out_trade_no
	 * @return
	 * @throws Exception
	 */
	public boolean delivernotify(String access_token, String openid, String transid, String out_trade_no)
			throws Exception;



}
