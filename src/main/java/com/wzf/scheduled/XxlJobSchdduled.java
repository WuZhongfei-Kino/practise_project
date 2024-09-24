package com.wzf.scheduled;

import com.wzf.pojo.Result;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import com.xxl.job.core.handler.annotation.XxlJob;

import org.springframework.stereotype.Component;

@Component
public class XxlJobSchdduled {
    @XxlJob("xxlJobTask")
    public ReturnT<String> execute(String param){
        String applicationName = XxlJobSpringExecutor.getApplicationContext().getApplicationName();
        System.err.println(applicationName +"====" + param);
        try{
            //模拟业务处理线程休眠10秒
            Thread.sleep(10000L);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.err.println("================每一分钟打印一次日志================");
        return ReturnT.SUCCESS;
    }
}
