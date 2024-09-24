package com.wzf.config;

import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XxlJobConfig {
    @Value("${xxlJob.accessToken}")
    private String accessToken;
    @Value("${xxlJob.adminAddress}")
    private String adminAddress; // http://localhost:8080/xxl-job-admin

//    executor:
    @Value("${xxlJob.executor.appname}")
    private String appname; //: testJob
    @Value("${xxlJob.executor.ip}")
    private String ip;//:
    @Value("${xxlJob.executor.port}")
    private int port;//: 0
    @Value("${xxlJob.executor.logpath}")
    private String logpath; //: logs/xxlJob
    @Value("${xxlJob.executor.logretentiondays}")
    private int logretentiondays; // : 5

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor(){
        System.out.println("=================== xxlJobExecutor init bean ======================");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAccessToken(accessToken);
        xxlJobSpringExecutor.setAdminAddresses(adminAddress);
        xxlJobSpringExecutor.setAppname(accessToken);
        xxlJobSpringExecutor.setIp(ip);
        xxlJobSpringExecutor.setPort(port);
        xxlJobSpringExecutor.setLogPath(logpath);
        xxlJobSpringExecutor.setLogRetentionDays(logretentiondays);
        return xxlJobSpringExecutor;
    }
}
