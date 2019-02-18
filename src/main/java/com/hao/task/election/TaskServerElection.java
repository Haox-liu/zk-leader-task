package com.hao.task.election;

import com.hao.task.util.HostUtil;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TaskServerElection {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TaskServerElection.class);

    private CuratorFramework client;
    private LeaderLatch leaderLatch;


    public TaskServerElection() throws Exception {
        this.start();
    }


    private void start() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        //client 有默认DEFAULT_SESSION_TIMEOUT_MS 60000ms, DEFAULT_CONNECTION_TIMEOUT_MS 15000ms
        //client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy);
        client = CuratorFrameworkFactory
                .builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(60000)
                .retryPolicy(retryPolicy)
                .build();
        client.getConnectionStateListenable().addListener(new ZKConnectionStateListener());
        leaderLatch = new LeaderLatch(client, "/scheduler/task",  "Server: " + HostUtil.hostAddress);
        leaderLatch.addListener(new ZKLeaderLatchListener());

        if (client.getState() != CuratorFrameworkState.STARTED) {
            client.start();
            logger.info("client is starting...");
        }

        if (leaderLatch.getState() != LeaderLatch.State.STARTED) {
            leaderLatch.start();
            logger.info("leaderLatch is starting...");
        }
    }


    public boolean isLeader() throws Exception {

        //选举等待，超时断开连接
        //return leaderLatch.await(30, TimeUnit.SECONDS);

        //选举等待，无超时，用作主备切换
        leaderLatch.await();
        return leaderLatch.hasLeadership();
    }


    public void stop() {
        CloseableUtils.closeQuietly(leaderLatch);
        CloseableUtils.closeQuietly(client);
    }

    public boolean isConnected() {
        return client.getZookeeperClient().isConnected();
    }

}
