package com.common.utils.encryption;

import java.util.Base64;

/**
 * base64 加密 解密
 *
 * @author: song
 * @data: 2020/7/26 0:34
 */
public class Base64Utils {

    /**
     * 使用jdk默认的base64 加密
     *
     * @param str 待加密的字符串
     * @return
     */
    public static String base64Encoder(String str) {
        return new String(Base64.getEncoder().encode(str.getBytes()));
    }

    /**
     * 使用jdk默认的base64 解密
     *
     * @param str 需解密的字符串
     * @return
     */
    public static String base64Decoder(String str) {
        return new String(Base64.getDecoder().decode(str));

    }

    public static void main(String[] args) {
        String s = base64Encoder("1002");
        System.out.println(s);
        System.out.println(base64Decoder(s));
    }


}
