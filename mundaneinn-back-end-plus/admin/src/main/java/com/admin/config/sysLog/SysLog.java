

package com.admin.config.sysLog;

import java.lang.annotation.*;

/**
 * 系统日志注解
 * @author idcsc
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

	/**
	 * 模块 操作描述
	 * 比如：订单管理-删除
	 */
	 String title() default "";

}
