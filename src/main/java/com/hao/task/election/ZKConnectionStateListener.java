package com.hao.task.election;

import com.hao.task.util.HostUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//连接状态监听
public class ZKConnectionStateListener implements ConnectionStateListener {
    private static final Logger logger = LoggerFactory.getLogger(ZKConnectionStateListener.class);

    @Override
    public void stateChanged(CuratorFramework client, ConnectionState state) {
        logger.info("Now the server {} state is {}", HostUtil.hostAddress, state.name());

        if (ConnectionState.CONNECTED != state) {
            try {
                logger.error("the server {} is disconnected", HostUtil.hostAddress);
                //断开连接后需要做报警处理，发邮件或短信
                client.getZookeeperClient().blockUntilConnectedOrTimedOut();
                logger.info("the server {} is reconnected", HostUtil.hostAddress);
            } catch (InterruptedException e) {
                logger.error("the server {} is down", HostUtil.hostAddress, e);
            }
        }
    }
}
