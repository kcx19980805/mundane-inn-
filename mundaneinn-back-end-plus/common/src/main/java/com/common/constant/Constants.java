package com.common.constant;

/**
 * 通用常量信息
 *
 * @author leibosong
 */
public class Constants {

    /**
     * 超级管理员Type  账号类型  1超级管理员  2普通管理员
     */
    public static final String SUPER_ADMIN = "1";
    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 5;

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 令牌过期时间
     */
    public static final String LOGIN_EXPIRE_TIME = "login_expire_time";

    /**
     * 资源映射路径 前缀 上传
     */
    public static final String RESOURCE_UPLOAD = "/kcxFiles/upload";

    /**
     * 资源映射路径 前缀 下载
     */
    public static final String RESOURCE_DOWNLOAD = "/kcxFiles/download";

    /**
     * redisKey
     */
    public static final String REDIS_KEY = "redisKey";

    /**
     * 请求headers携带参数
     */
    public static final String TOKEN = "X-User-Token";

    /**
     * token中的key
     */
    public static final String TOKEN_UUID = "uuid";
    //更新时间
    public static final String TOKEN_UPDATE_TIME = "updateTime";
    //过期时间
    public static final String TOKEN_EXPIRE_TIME = "expireTime";
    /**
     * 账号表的id
     */
    public static final String TOKEN_ACCOUNT_ID = "accountId";
    /**
     * 登录用户id,不是账号表的id
     */
    public static final String TOKEN_USERID = "userId";
    /**
     * 1厂家 2服务商 3联营商 4BD经理 5补货员
     */
    public static final String TOKEN_USER_TYPE = "userType";

    /**
     * token 密钥 secret
     */
    public static final String TOKEN_SECRET = "adsguyasehixuwsharf3258==ewrhjfuiaweyriuQWEWARTVAWE5RWEARRV59@#$%^&*RE174T8NESR7Y86E4VTAQW2IK3R9O87X12.,,.RTYNERST3PQOI45902F";
    /**
     * token 有效时间戳 默认30天
     */
    public static final long TOKEN_EXPIRE_TIME2 = 60L * 1000 * 60 * 24 * 30;

    /**
     * 分页 页码
     */
    public static final String PAGE = "page";
    /**
     * 分页 每页条数
     */
    public static final String LIMIT = "limit";
    /**
     * 分页 序号排序 desc降序 asc升序
     */
    public static final String ORDER = "order";
    /**
     * 分页 序号 从1开始
     */
    public static final String IID = "iid";

    /**
     * 状态码
     */
    public static final String CODE = "code";
    /**
     * 返回内容
     */
    public static final String MESSAGE = "msg";
    /**
     * 数据对象
     */
    public static final String DATA = "data";

    /**
     * 成功后的提示内容
     */
    public static final String SUCCESS = "success";

    /**
     * 服务器失败/服务连接异常
     */
    public static final String ERROR = "error";

    /**
     * 服务器失败/服务连接异常
     */
    public static final String SERVICE_ERROR = "该服务暂不可用,请稍后再试";

    /**
     * 授权过期 登录过期
     */
    public static final String MESSAGE_LOGOUT = "登录过期";

    /**
     * 没有操作权限
     */
    public static final String UNAUTHORIZED = "没有操作权限,请联系管理员授权";


}
