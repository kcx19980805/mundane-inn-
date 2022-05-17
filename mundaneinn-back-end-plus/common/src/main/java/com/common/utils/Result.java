package com.common.utils;

import com.common.constant.Constants;
import com.common.constant.HttpStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回数据对象
 * {
 * 	"code": 0,
 * 	"data": {},
 * 	"msg": ""
 * }
 *
 * @author
 */

@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("数据")
    private T data;

    @ApiModelProperty("消息状态码")
    private int code;

    @ApiModelProperty("消息内容")
    private String msg;

    /**
     * 数据对象
     */
    public Result() {

    }

    /**
     * 初始化一个新创建的 Result 对象
     *
     * @param data 数据对象
     */
    public Result(T data) {
        this.data = data;
    }

    /**
     * 初始化一个新创建的 Result 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     */
    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 初始化一个新创建的 Result 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public Result(int code, String msg, T data) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static <T> Result<T> ok(T data) {
        Result response = new Result();
        response.setData(data);
        response.setCode(HttpStatus.SUCCESS);
        response.setMsg(Constants.SUCCESS);
        return response;
    }

    public static <T>Result<T> ok() {
        Result response = new Result();
        response.setCode(HttpStatus.SUCCESS);
        response.setMsg(Constants.SUCCESS);
        return response;
    }

    public static <T>Result<T> ok(String msg) {
        Result response = new Result();
        response.setCode(HttpStatus.SUCCESS);
        response.setMsg(msg);
        return response;
    }

    public static <T>Result<T> ok(String msg, T data) {
        Result response = new Result();
        response.setCode(HttpStatus.SUCCESS);
        response.setMsg(msg);
        response.setData(data);
        return response;
    }

    public static <T> Result<T> error(T data) {
        Result response = new Result();
        response.setData(data);
        response.setCode(HttpStatus.ERROR);
        response.setMsg(Constants.ERROR);
        return response;
    }


    public static <T>Result<T> error() {
        Result response = new Result();
        response.setCode(HttpStatus.ERROR);
        response.setMsg(Constants.ERROR);
        return response;
    }

    public static <T>Result<T> error(String msg) {
        Result response = new Result();
        response.setCode(HttpStatus.ERROR);
        response.setMsg(msg);
        return response;
    }

    public static <T>Result<T> error(int code, String msg) {
        Result response = new Result();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

    public static <T>Result<T> error(String msg, T data) {
        Result response = new Result();
        response.setData(data);
        response.setMsg(msg);
        return response;
    }

    /**
     * 根据sql语句 影响的行数判断成功 失败
     * 未抛异常 数据事务回滚自己处理(rows=0的情况)
     *
     * @param rows
     * @return
     */
    public static Result toRow(int rows) {
        return rows > 0 ? Result.ok() : Result.error();
    }
}
