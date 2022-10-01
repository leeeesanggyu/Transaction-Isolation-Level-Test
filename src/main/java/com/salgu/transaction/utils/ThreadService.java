package com.salgu.transaction.utils;

import org.springframework.stereotype.Component;

public class ThreadService {
    static public void threadSleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
