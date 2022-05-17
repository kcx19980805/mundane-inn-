package com.common.utils.encryption;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

// 匹配App端的md5加密
public final class MD5Utils {
    //小写加密
    private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    //大写加密
    /*private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};*/

    /**
     * 对文件使用md5算法完成加密
     */
    public static String encode(File file) {
        FileInputStream in = null;
        MessageDigest md5 = null;
        try {
            in = new FileInputStream(file);
            FileChannel ch = in.getChannel();
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY,
                    0, file.length());
            md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return toHex(md5.digest());
    }


    private static String toHex(byte[] bytes) {
        StringBuffer str = new StringBuffer(32);
        for (byte b : bytes) {
            str.append(hexDigits[(b & 0xf0) >> 4]);
            str.append(hexDigits[(b & 0x0f)]);
        }
        return str.toString();
    }

    /**
     * MD5加密字符串32位
     *
     * @param str 字符串
     * @return MD5加密后的字符串
     */
    public static String get32MD5String(String str) {
        try {
            return getMD5String(str.getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * MD5加密字符串16位
     *
     * @param str 字符串
     * @return MD5加密后的字符串
     */
    public static String get16MD5String(String str) {
        try {
            return get32MD5String(str).substring(8, 24);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * MD5加密以byte数组表示的字符串
     *
     * @param bytes 目标byte数组
     * @return MD5加密后的字符串
     */
    public static String getMD5String(byte[] bytes) {

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toHex(md5.digest());
    }
}
