package com.hao.task;

import com.hao.task.election.TaskServerElection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TaskApplication {

	public static void main(String[] args) throws Exception {

        TaskServerElection taskServerElection = new TaskServerElection();

        try {
            if (taskServerElection.isLeader()) {
                SpringApplication.run(TaskApplication.class, args);
            }
        } catch (Exception e) {
            e.printStackTrace();
            taskServerElection.stop();
        }

	}

}

