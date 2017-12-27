/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.bmsoft.base.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符工具类
 *
 */
public class StringUtil {

    public static String convertIllegalStrings(String source) {
        source = StringUtils.replace(source, "<", "&lt;");
        source = StringUtils.replace(source, ">", "&gt;");
        return source;
    }

    /**
     * 转码，微信会有表情符，插入库会乱码。
     * @param str
     * @return
     * @throws java.io.IOException
     */
    public static String encode(String str) {
        if(str == null) {
            return str;
        }
        byte[] strByte = str.getBytes();
        for (int i = 0; i < strByte.length; i++) {
            if ((strByte[i] & 0xF8) == 0xF0) {

                for (int j = 0; j < 4; j++) {
                    if(i+j < strByte.length) {
                        strByte[i+j]=0x30;
                    }
                }
                i += 3;
            }
        }
        str = new String(strByte);
        return str.replaceAll("0000", "");
    }

    /**
     * 判断目标字符串是否存在匹配指定的正则表达式的子字符串
     *
     * @param patternStr 指定正则表达式
     * @param srcStr     目标字符串
     * @return true有 false无
     */
    public static boolean isIn(String patternStr, String srcStr) {
        Pattern p = Pattern.compile(patternStr);
        Matcher m = p.matcher(srcStr);
        return m.find();
    }

    /**
     * MD5密码加密
     *
     * @param pwd
     * @return
     */
    public static String md5Pwd(String pwd) {
        if(pwd == null || pwd.isEmpty()) {
            return "";
        } else if(pwd.length() != 32) { //不等于32,表示未加密过,加密两次
            return DigestUtils.md5Hex(DigestUtils.md5Hex(pwd));
        } else {
            return DigestUtils.md5Hex(pwd);
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(md5Pwd("123456"));//14e1b600b1fd579f47433b88e8d85291
    }

}
