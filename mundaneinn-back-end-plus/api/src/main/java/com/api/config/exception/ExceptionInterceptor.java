package com.api.config.exception;

import com.common.constant.HttpStatus;
import com.common.exception.CustomException;
import com.common.utils.Result;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器，避免大量try-catch
 * @RestControllerAdvice捕捉全局存在@RestController注解的类的异常
 * @ControllerAdvice捕捉全局存在@Controller注解的类的异常
 */
@RestControllerAdvice
public class ExceptionInterceptor {

    /**
     * 自定义异常
     */
    @ExceptionHandler(CustomException.class)
    public Result handleRRException(CustomException e) {
        return handleException(e,e.getCode(),e.getMessage());
    }

    /**
     * shiro异常
     */
    @ExceptionHandler(AuthorizationException.class)
    public Result handleAuthorizationException(AuthorizationException e) {
        return handleException(e,HttpStatus.ERROR,"没有权限，请联系管理员授权");
    }

    /**
     * 验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result validExceptionHandler(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return handleException(e,HttpStatus.ERROR,message);
    }

    /**
     * 验证异常
     */
    @ExceptionHandler(BindException.class)
    public Result validatedBindException(BindException e) {
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return handleException(e,HttpStatus.ERROR,message);
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        return handleException(e,HttpStatus.ERROR,"未知异常,请联系管理员");
    }

    /**
     * 统一处理异常
     * @param code
     * @param message
     * @return
     */
    Result handleException(Exception e,int code,String message){
        e.printStackTrace();
        Result response = new Result();
        response.setCode(code);
        response.setMsg(message);
        return response;
    }
}
