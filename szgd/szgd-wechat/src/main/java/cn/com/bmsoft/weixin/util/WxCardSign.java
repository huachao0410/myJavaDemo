package cn.com.bmsoft.weixin.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 微信卡券签名
 */
public class WxCardSign {

    private ArrayList<String> m_param_to_sign;

    public WxCardSign() {
        m_param_to_sign = new ArrayList<String>();
    }

    public void addData(String value) {
        m_param_to_sign.add(value);
    }

    public void addData(Integer value) {
        m_param_to_sign.add(value.toString());
    }

    public String getSignature() {
        Collections.sort(m_param_to_sign);
        StringBuilder string_to_sign = new StringBuilder();
        for (String str : m_param_to_sign) {
            string_to_sign.append(str);
        }
        System.out.println("card_to_sign:"+string_to_sign);

        try {
            MessageDigest hasher = MessageDigest.getInstance("SHA-1");
            byte[] digest = hasher.digest(string_to_sign.toString().getBytes());
            return byteToHexString(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String byteToHexString(byte[] data) {
        StringBuilder str = new StringBuilder();
        for (byte b : data) {
            String hv = Integer.toHexString(b & 0xFF);
            if (hv.length() < 2)
                str.append("0");
            str.append(hv);
        }
        return str.toString();
    }

}

