package com.listener;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author rtw
 * @since 2020/1/5
 */
@Slf4j
public class AsyncEventRejectHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        try {
            executor.getQueue().put(r);
        } catch (InterruptedException e) {
            log.error(" !!! Async Event Reject, Error :", e);
            Thread.currentThread().interrupt();
        }
    }
}

