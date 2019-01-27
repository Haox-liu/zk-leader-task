package com.hao.task.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskOne {

    @Scheduled(cron = "0/2 * * * * *")
    public void task1() {
        System.err.println("task_one run......");
    }
}
