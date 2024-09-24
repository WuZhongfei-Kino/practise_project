package com.wzf.aspect;

import com.wzf.anno.RedisLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
public class RedisLockAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Pointcut("@annotation(com.wzf.anno.RedisLock)")
    public void redisLockPointCut(){}

    @Around("redisLockPointCut()")
    public void around(ProceedingJoinPoint point) throws Throwable {
        Method  method = currentMethod(point);//获取方法

        //获取方法的注解对象
        RedisLock redisLock = method.getAnnotation(RedisLock.class);
        //获取锁的名称
        String methodName = redisLock.name();
        if (!StringUtils.hasLength(methodName)){
            //如果注解里没有设置锁的名称，默认使用方法的名称
            methodName = method.getName();
            //获取锁的标识
            boolean flag = true;

            int retryCount = redisLock.retry();
            do {
                //如果锁的标识为true和重试次数大于0
                if (!flag && retryCount > 0){
                    //线程休眠
                    Thread.sleep(1000L);
                    //重试次数 --
                    retryCount --;
                }

                //尝试获取锁
                flag = stringRedisTemplate.opsForValue().setIfAbsent(methodName, "1", redisLock.expired(), TimeUnit.SECONDS);
                //获取到锁过期
                if (flag) {break;}
                //根据配置的重试次数，执行n次获取锁的方法，默认不重试
            }while (retryCount > 0);

            if (flag){
                try {
                    point.proceed();
                }catch (Throwable e){
                    System.out.println("异常通知方法》目标方法名:+"+method.getName()+",异常："+ e);
                }finally {
                    //删除key
                    stringRedisTemplate.delete(methodName);
                }
                System.out.println("执行》目标方法名:+"+method.getName()+"未获取锁,重试次数："+ retryCount);
            }
        }



    }

    /**
     * 根据切入点获取执行的方法
     * @param point
     * @return
     */
    private Method currentMethod(ProceedingJoinPoint point) {
         String methodName = point.getSignature().getName();
         //获取目标类的所有方法，找到当前要执行的方法
         Method[] methods = point.getTarget().getClass().getMethods();
         Method resultMethod = null;
        for (Method method: methods) {
            if (method.getName().equals(methodName)){
                resultMethod = method;
                break;
            }
        }
        return resultMethod;
    }
}

