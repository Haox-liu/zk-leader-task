package com.hao.task.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Task {

    @Scheduled(cron = "0/2 * * * * *")
    public void task1() {
        System.err.println("task1 run......");
    }

//    @Scheduled(cron = "0/3 * * * * *")
//    public void task2() {
//        System.out.println("task2 run......");
//    }
//
//    @Scheduled(cron = "0/4 * * * * *")
//    public void task3() {
//        System.out.println("task3 run......");
//    }

}
