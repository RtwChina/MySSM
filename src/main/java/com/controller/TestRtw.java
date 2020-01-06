package com.controller;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author rtw
 * @since 2019/12/16
 */
@Component
public class TestRtw implements SmartLifecycle {

    private boolean isRunning = false;


    @Override
    public boolean isAutoStartup() {
        // 需要手动设置为true，让容器自动触发 start 方法
        return true;
    }

    @Override
    public void stop(Runnable callback) {
        // 只有isRunning返回true，容器销毁 才会运行stop方法。
    }

    @Override
    public void start() {
        // 容器开始启动的时候调用，注意看到修改了isRunning。
        System.out.println("start smart");
        isRunning = true;
    }

    @Override
    public void stop() {

    }

    /**
     * 返回当前Lifecycle的状态
     */
    @Override
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * 优先级
     */
    @Override
    public int getPhase() {
        return 0;
    }
}
