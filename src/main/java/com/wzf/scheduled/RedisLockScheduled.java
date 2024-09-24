package com.wzf.scheduled;

import com.wzf.anno.RedisLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RedisLockScheduled {

    @Scheduled(cron = "0 */1 * * * ?")
    @RedisLock(name = "testRedisLock", expired = 30, retry = 3)
    public void testRedisLock(){
        try{
            //模拟业务处理线程休眠10秒
            Thread.sleep(10000L);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.err.println("================每一分钟打印一次日志================");
    }
}
