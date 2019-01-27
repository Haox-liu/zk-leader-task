package com.hao.task;

import com.hao.task.election.TaskServerElection;
import com.hao.task.util.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@EnableScheduling
@SpringBootApplication
public class TaskApplication {

	public static void main(String[] args) throws Exception {

        TaskServerElection taskServerElection = new TaskServerElection();

        if (taskServerElection.isLeader()) {
            SpringApplication.run(TaskApplication.class, args);
        }

//        while (true) {
//            ThreadPoolTaskScheduler threadPoolTaskScheduler = SpringUtil.getBean(ThreadPoolTaskScheduler.class);
//
//            if (!taskServerElection.isConnected()) {
//                System.err.println(taskServerElection.isLeader());
//                //stop task
//                threadPoolTaskScheduler.getScheduledThreadPoolExecutor().shutdownNow();
//                System.err.println("stop task now");
//                System.out.println(threadPoolTaskScheduler);
//                taskServerElection.stop();
//            } else {
//                if (threadPoolTaskScheduler.getScheduledThreadPoolExecutor().isShutdown()) {
//                    SpringApplication.run(TaskApplication.class, args);
//                    System.out.println("restart task");
//                }
//            }
//
//            Thread.sleep(5000);
//        }

//        ThreadPoolTaskScheduler threadPoolTaskScheduler = SpringUtil.getBean(ThreadPoolTaskScheduler.class);
//        System.out.println(threadPoolTaskScheduler.getPoolSize());
//        System.out.println(threadPoolTaskScheduler.getScheduledThreadPoolExecutor().getQueue().size());
//
//        Thread.sleep(10000);
//
//        threadPoolTaskScheduler.getScheduledThreadPoolExecutor().shutdownNow();

	}

}

