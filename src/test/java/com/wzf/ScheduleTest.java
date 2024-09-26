package com.wzf;

import com.wzf.anno.RedisLock;
import com.wzf.scheduled.RedisLockScheduled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest//如果在测试类上添加了这个注解， 那么将来单元测试方法执行之前，会先初始化Spring容器
public class ScheduleTest {

    @Scheduled(cron = "0 */1 * * * ?")
    @RedisLock(name = "testRedisLock", expired = 30, retry = 3)
    @Test
    public void testRedisLock(){
        try{
            //模拟业务处理线程休眠10秒
            Thread.sleep(10000L);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.err.println("================每一分钟打印一次日志================");
    }


    @Autowired
    private RedisLockScheduled scheduled;
    @Test
    public void testSchdule(){
        scheduled.testRedisLock();
    }
}
