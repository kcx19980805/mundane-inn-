package com.admin.config.sysLog;


import com.admin.config.shiro.ShiroUtils;
import com.admin.sys.entity.SysOperLogEntity;
import com.admin.sys.entity.SysUserEntity;
import com.admin.sys.service.SysOperLogService;
import com.alibaba.fastjson.JSON;
import com.common.utils.IpUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 系统日志，切面处理类
 */
@Aspect
@Component
public class SysLogAspect {
    @Autowired
    private SysOperLogService sysOperLogService;

    //统计请求的处理时间
    Long startTime = null;


    /**
     * 配置织入点 @sysLog注解
     */
    @Pointcut("@annotation(com.admin.config.sysLog.SysLog)")
    public void logPointCut() {

    }

    /**
     * 异常切入点记录异常日志 扫描所有controller包下操作
     */
/*    @Pointcut("execution(* com.admin.sys.controller..*.*(..))")
    public void exceptionLogPointCut() {
    }*/


    @Before(value = "logPointCut()")
    public void before(JoinPoint joinPoint) {
        startTime = System.currentTimeMillis();
    }


    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        saveSysLog((ProceedingJoinPoint) joinPoint, System.currentTimeMillis() - startTime, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        saveSysLog((ProceedingJoinPoint) joinPoint, System.currentTimeMillis() - startTime, e, e);
    }


    /**
     * @param joinPoint
     * @param time       操作时长
     * @param e          异常
     * @param jsonResult 返回参数
     */
    private void saveSysLog(ProceedingJoinPoint joinPoint, long time, final Exception e, Object jsonResult) {

        // 获得注解
        SysLog syslog = getAnnotationLog(joinPoint);
        if (syslog == null) {
            return;
        }
        SysOperLogEntity sysLog = new SysOperLogEntity();
        sysLog.setTitle(syslog.title());

        //获取request
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();
        //设置IP地址
        sysLog.setOperIp(IpUtils.getIpAddr(request));

        Object object = ShiroUtils.getObject();

        SysUserEntity sysUserEntity = (SysUserEntity) object;
        //操作人员账号
        sysLog.setOperUserAccount(sysUserEntity.getAccount());
        sysLog.setOperUserType("0");
        sysLog.setOperUserId(sysUserEntity.getId());

        if (e != null) {
            sysLog.setStatus(1);
            sysLog.setErrorMsg(e.getMessage());
        }
        //设置请求参数
        setRequestValue(joinPoint, sysLog);
        // 返回参数
        sysLog.setJsonResult(JSON.toJSONString(jsonResult));
        sysLog.setOperTime(time);
        sysLog.setCreateTime(LocalDateTime.now());
        //保存系统日志
        sysOperLogService.save(sysLog);
    }


    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(JoinPoint joinPoint, SysOperLogEntity operLog) {

        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();
        //请求方法
        String method = request.getMethod().toLowerCase();
        if ("put".equals(method) || "post".equals(method)) {
            String params = argsArrayToString(joinPoint.getArgs());
            operLog.setOperParam(StringUtils.substring(params, 0, 20000));
        } else {
/*            Map<?, ?> paramsMap = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            System.out.println("get==delete=="+JSON.toJSONString(paramsMap.toString()));
            System.out.println(request.getQueryString()+ "=="+request.getParameterMap());*/
            operLog.setOperParam(StringUtils.substring(request.getQueryString(), 0, 20000));
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private SysLog getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(SysLog.class);
        }
        return null;
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (int i = 0; i < paramsArray.length; i++) {
                if (!isFilterObject(paramsArray[i])) {
                    Object jsonObj = JSON.toJSON(paramsArray[i]);
                    params += jsonObj.toString() + " ";
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    public boolean isFilterObject(final Object o) {
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
    }
}
