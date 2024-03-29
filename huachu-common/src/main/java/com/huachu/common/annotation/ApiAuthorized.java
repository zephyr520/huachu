package com.huachu.common.annotation;

import java.lang.annotation.*;

/**
 * @author Administrator
 * @DATE 2018/8/24
 * @description 操作权限注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiAuthorized {

    String value() default "";
}
