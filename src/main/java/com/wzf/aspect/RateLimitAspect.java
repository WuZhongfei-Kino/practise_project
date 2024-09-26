package com.wzf.aspect;


import com.revinate.guava.util.concurrent.RateLimiter;
import com.wzf.anno.Limiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 1.Google的Guava工具包中就提供了一个限流工具类--RateLimiter, RateLimiter是基于"令牌桶算法"来实现限流
 * 2.@Aspect
 */
@Aspect
@Component
@Slf4j
public class RateLimitAspect {
    //定义一个并发环境的ConcurrentHashMap, 用来存放不同方法的限流器
    private ConcurrentHashMap<String, RateLimiter> RATE_LIMITER = new ConcurrentHashMap<String, RateLimiter>();
    private RateLimiter rateLimiter;

    /**
     * 切入点。切面
     */
    @Pointcut("@annotation(com.wzf.anno.Limiter)")
    public void serviceLimit(){

    }

    @Around("serviceLimit()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //获取拦截的方法名
        Signature signature = point.getSignature();
        //获取的方法名
        MethodSignature methodSignature = (MethodSignature) signature;
        //返回被织入增加处理目标对象
        Object target = point.getTarget();
        //为了获取注解信息
        Method currentMethod = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        //获取注解信息
        Limiter annotation = currentMethod.getAnnotation(Limiter.class);
        //获取注解每秒加入桶中的token
        double limitNum = annotation.limitNum();
        //注解所在的方法名 区分不同的限流策略
        String methodName = methodSignature.getName();

        //判断方法名是否在限流器中
        if (RATE_LIMITER.containsKey(methodName)){
            rateLimiter = RATE_LIMITER.get(methodName);
        } else {
            RATE_LIMITER.put(methodName, RateLimiter.create(limitNum));
            rateLimiter = RATE_LIMITER.get(methodName);
        }

        //判断访问次数
        if (rateLimiter.tryAcquire()){
            log.info("流量控制策略范围内");
            System.out.println("流量控制策略范围内");
            return point.proceed();
        }else {
            log.info("被限流了。。。");
            System.out.println("被限流了。。。");
            throw new RuntimeException("手速太快了， 休息一下再试试吧。");
        }

    }

}
