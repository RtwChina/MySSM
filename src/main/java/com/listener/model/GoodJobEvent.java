package com.listener.model;

import lombok.Data;

/**
 * @author rtw
 * @since 2020/1/5
 */
@Data
public class GoodJobEvent {
    String msg;

    public GoodJobEvent(String msg) {
        this.msg = msg;
    }
}
