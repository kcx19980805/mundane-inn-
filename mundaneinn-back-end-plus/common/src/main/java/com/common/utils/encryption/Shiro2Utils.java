package com.common.utils.encryption;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * Shiro工具类
 */
public class Shiro2Utils {
    /**
     * 密码加密盐值长度 随机字符
     */
    public final static int SALT_LENGTH = 20;
    /**
     * 加密算法 SHA-512 默认使用
     */
    public final static String HASH_ALGORITHM_NAME_SHA512 = "SHA-512";
    /**
     * 加密算法 SHA-256
     */
    public final static String HASH_ALGORITHM_NAME_SHA256 = "SHA-256";
    /**
     * 循环次数
     */
    public final static int hashIterations = 16;

    /**
     * 生成密码 SHA-256
     *
     * @param password
     * @param salt
     * @return
     */
    public static String sha256(String password, String salt) {
        return new SimpleHash(HASH_ALGORITHM_NAME_SHA256, password, salt, hashIterations).toString();
    }

    /**
     * 生成密码 SHA-512
     *
     * @param password
     * @param salt
     * @return
     */
    public static String sha512(String password, String salt) {
        return new SimpleHash(HASH_ALGORITHM_NAME_SHA512, password, salt, hashIterations).toString();
    }


    /**
     * 校验密码是否正确 加密SHA-512
     *
     * @param password    数据库存的正确密码
     * @param oldPassword 原密码 前端传入的 未加密
     * @param salt        盐
     * @return true正确 false错误
     */
    public static boolean checkPasswordSha512(String password, String oldPassword, String salt) {
        oldPassword = new SimpleHash(HASH_ALGORITHM_NAME_SHA512, oldPassword, salt, hashIterations).toString();
        return password.equals(oldPassword);
    }

    /**
     * 校验密码是否正确 加密SHA-256
     *
     * @param password    数据库存的正确密码
     * @param oldPassword 原密码 前端传入的 未加密
     * @param salt        盐
     * @return true正确 false错误
     */
    public static boolean checkPasswordSha256(String password, String oldPassword, String salt) {
        oldPassword = new SimpleHash(HASH_ALGORITHM_NAME_SHA256, oldPassword, salt, hashIterations).toString();
        return password.equals(oldPassword);
    }

    /**
     * 随机生成加密盐 字母大小写 不含数字
     *
     * @return
     */
    public static String getGenerateSalt() {
        return RandomStringUtils.randomAlphabetic(SALT_LENGTH);
    }

    public static void main(String[] args) {
        String password = Base64Utils.base64Encoder("123456");
        String salt = getGenerateSalt();
        String password2 = sha256(password, salt);
        System.out.println(password + "===" + salt + "==" + password2);
    }
}
