package com.wzf.config;

import com.wzf.interceptors.LoginInterceptor;
import com.wzf.interceptors.UrlInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private UrlInterceptor urlInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录接口和注册接口不拦截
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/user/register","/user/login");
//        registry.addInterceptor(urlInterceptor).addPathPatterns("/**");
//        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
