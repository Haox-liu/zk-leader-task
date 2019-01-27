package com.hao.task.election;

import com.hao.task.util.HostUtil;
import com.hao.task.util.SpringUtil;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;

//leader选举监听
public class ZKLeaderLatchListener implements LeaderLatchListener {
    private static final Logger logger = LoggerFactory.getLogger(ZKLeaderLatchListener.class);

    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

    @Override
    public void isLeader() {
        logger.info("The server {} is leader", HostUtil.hostAddress);
        if (threadPoolTaskScheduler != null) {
            System.out.println(threadPoolTaskScheduler);
            System.out.println("queue.size...." + queue.size());

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (queue.size() > 0) {
                threadPoolTaskScheduler.submit(queue.poll());
            }
        }

    }

    @Override
    public void notLeader() {
        logger.info("The server {} is not leader", HostUtil.hostAddress);

        threadPoolTaskScheduler = SpringUtil.getBean(ThreadPoolTaskScheduler.class);
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = threadPoolTaskScheduler.getScheduledThreadPoolExecutor();
        while (!scheduledThreadPoolExecutor.getQueue().isEmpty()) {
            Runnable r = scheduledThreadPoolExecutor.getQueue().poll();
            if (r != null) {
                //threadPoolTaskScheduler.submit(r);
                queue.add(r);
            }
        }
        System.out.println(scheduledThreadPoolExecutor.getQueue().size());
        System.err.println("stop task now");
    }
}
