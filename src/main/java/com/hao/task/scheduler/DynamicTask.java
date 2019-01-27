package com.hao.task.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

//@Component
public class DynamicTask {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    //在ScheduledFuture中有一个cancel可以停止定时任务
    private ScheduledFuture<?> future;

    /**
     * ThreadPoolTaskScheduler：线程池任务调度类，能够开启线程池进行任务调度。
     * ThreadPoolTaskScheduler.schedule()方法会创建一个定时计划ScheduledFuture，在这个方法需要添加两个参数，Runnable（线程接口类） 和CronTrigger（定时任务触发器）
     */
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(10);
        return threadPoolTaskScheduler;
    }

    @PostConstruct
    public String startTask() throws Exception {

        future = threadPoolTaskScheduler.schedule(new Thread(
                () -> {
                    System.out.println("run thread1....");
                    System.out.println(new Date());
                    System.out.println(threadPoolTaskScheduler.getPoolSize());
                }
        ), new CronTrigger("0/2 * * * * *"));


        Thread.sleep(11000);

        future.cancel(true);

        return "startTask";
    }

}

