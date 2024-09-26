package com.wzf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling //可定时的注解
@SpringBootApplication
public class BigEventApplication {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        SpringApplication.run(BigEventApplication.class, args);
    }
}