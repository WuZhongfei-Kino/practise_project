package com.wzf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest//如果在测试类上添加了这个注解， 那么将来单元测试方法执行之前，会先初始化Spring容器
public class RedisTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testSet(){
        System.out.println(redisTemplate);
        //往redis中存储一个键值对 StringRedisTemplate
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set("username", "zs");
        operations.set("id","1", 15, TimeUnit.SECONDS);
    }
    @Test
    public void testGet(){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String username = operations.get("username");
        System.out.println(username);
    }
}
