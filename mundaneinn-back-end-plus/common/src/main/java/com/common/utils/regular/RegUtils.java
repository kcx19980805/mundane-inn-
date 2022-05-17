package com.common.utils.regular;


import org.apache.commons.lang3.StringUtils;

/**
 * 正则表达式 校验
 */
public class RegUtils {
    /**
     * 手机号
     */
    public static final String Phone ="^1[0-9]{10}$";
    /**
     * 身份证 15-18位
     */
    public static final String IdCard = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
    /**
     * 邮箱匹配
     */
    public static final String Email = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    /**
     * 非零正整数
     */
    public static final String Number = "^[1-9]\\d*$";
    /**
     * 汉字
     */
    public static final String TEXT = "^[\\u4e00-\\u9fa5]{0,}$";
    /**
     * 域名
     */
    public static final String DOMAIN_NAME = "[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\\.?";
    /**
     * 金额 (不能为0) 小数点后两位 0.01 0.1 0.10 1.0 1.01 1
     */
    public static final String MONEY = "^((0(\\.[0][1-9]))|(0(\\.[1-9][0-9]?))|(([1-9][0-9]*)+(\\.[0-9]{1,2})?))$";
    /**
     * 金额 (能为0) 小数点后两位 0.00 0.01 0.1 0.10 1.0 1.01 1
     */
    public static final String MONEY2 = "^(0|(0(\\.[0][0-9]))|(0(\\.[1-9][0-9]?))|(([1-9][0-9]?)+(\\.[0-9]{1,2})?))$";

    /**
     * 零和非零开头的数字
     */
    public static final String NUMBER2 = "^(0|[1-9][0-9]*)$";
    /**
     * 非零开头的最多带两位小数的数字
     */
    public static final String NUMBER3 = "^([1-9][0-9]*)+(\\.[0-9]{1,2})?$";

    /**
     * 身份证 15-18位
     *
     * @param str
     * @return true：格式正确  false:格式错误
     */
    public static boolean regIdCard(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        } else if (!str.matches(IdCard)) {
            return false;
        }
        return true;
    }

    /**
     * 邮箱地址
     *
     * @param str
     * @return true：格式正确  false:格式错误
     */
    public static boolean regEmail(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        } else if (!str.matches(Email)) {
            return false;
        }
        return true;
    }

    /**
     * 汉字
     *
     * @param str
     * @return true：格式正确  false:格式错误
     */
    public static boolean regText(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        } else if (!str.matches(TEXT)) {
            return false;
        }
        return true;
    }


    /**
     * 非零正整数
     *
     * @param str
     * @return true：格式正确  false:格式错误
     */
    public static boolean regNumber(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        } else if (!str.matches(Number)) {
            return false;
        }
        return true;
    }

    /**
     * 金额 不能为0 小数点后2位
     *
     * @param str
     * @return true：格式正确  false:格式错误
     */
    public static boolean regMoney(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        } else if (!str.matches(MONEY)) {
            return false;
        }
        return true;
    }

    /**
     * 金额 能为0 小数点后2位
     *
     * @param str
     * @return true：格式正确  false:格式错误
     */
    public static boolean regMoney2(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        } else if (!str.matches(MONEY2)) {
            return false;
        }
        return true;
    }

    /**
     * 域名
     *
     * @param str
     * @return true：格式正确  false:格式错误
     */
    public static boolean regDomainName(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        } else if (!str.matches(DOMAIN_NAME)) {
            return false;
        }
        return true;
    }
}
