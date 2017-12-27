package cn.com.bmsoft.base.common.service.face;

/**
 * 短信服务
 */
public interface ISendMessageService {

    String sendSmsValidateNumber(String phone) throws Exception;

    boolean validateNumber(String phone, String number);

}
