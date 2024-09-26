package com.wzf.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Limiter {
    //默认放入桶中的令牌数
    double limitNum() default 5;
    //限流器名称, 使用该注解的每一个方法上，保证全局唯一性
    String name() default "";
}
