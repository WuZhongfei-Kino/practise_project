package com.wzf.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MINUTES;

@Component
public class UrlInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization") == null ? "visiter": request.getHeader("Authorization") ;
        System.out.println("preHandle。。。。"+token);
        String key = "rate_limit:" + token;
        Long count = redisTemplate.opsForValue().increment(key);
        System.out.println(count+"-->");
        if (count == 1 ){
            //设置过期时间
            redisTemplate.expire(key, 10, MINUTES);
        }
        Long MAX_REQUEST = 10L;
        if (count > MAX_REQUEST) {
            System.out.println("超过最大访问次数...");
            return false;
        }else {
            return true;
        }

    }
}
