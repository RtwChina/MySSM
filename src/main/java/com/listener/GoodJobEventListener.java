package com.listener;

import com.alibaba.fastjson.JSON;
import com.listener.model.GoodJobEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author rtw
 * @since 2020/1/5
 */
@Component
@Slf4j
public class GoodJobEventListener {

    @TransactionalEventListener
    public void goodJobEventListener(GoodJobEvent goodJobEvent) {
//        if(goodJobEvent.getApplicationContext().getParent() == null){ //root application context
//        }
        log.info("监听到goodJobEvent={}", JSON.toJSONString(goodJobEvent));

    }
}
