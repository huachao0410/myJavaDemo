package cn.com.bmsoft.weixin.util;

/**
 * Created by gzh on 2017/8/22.
 * 常量
 */
public class WXPayConstants {
	public enum SignType {
		MD5, HMACSHA256
	}
	public static final String FAIL     = "FAIL";
	public static final String SUCCESS  = "SUCCESS";
	public static final String HMACSHA256 = "HMAC-SHA256";
	public static final String MD5 = "MD5";

	public static final String FIELD_SIGN = "sign";
	public static final String FIELD_SIGN_TYPE = "sign_type";
}
