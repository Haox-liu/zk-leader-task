package com.hao.task.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostUtil {

    public static String hostAddress;

    static {
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
