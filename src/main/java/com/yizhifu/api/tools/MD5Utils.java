package com.yizhifu.api.tools;
import java.security.MessageDigest;


/**
 * @Title   MD5.java
 * @Package com.pro.huanbao.common.utils
 * @author  wanpu_ly
 * @dade    2017年9月28日 下午4:03:52
 * @version V1.0
 * 类说明:	MD5加密工具类
 */
public class MD5Utils {

    public static void main(String[] args) {
        String md5 = EncoderByMd5("123456");
        System.err.println(md5);
    }

    public static String EncoderByMd5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(s.getBytes("utf-8"));
            String hex = toHex(bytes);
            String code = hex.toLowerCase();
            return code;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String toHex(byte[] bytes) {

        final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i=0; i<bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }
}
