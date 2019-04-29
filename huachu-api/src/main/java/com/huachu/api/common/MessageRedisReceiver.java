package com.huachu.api.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @DATE 2018/10/15
 */
@Component
public class MessageRedisReceiver {

    private static final Logger logger = LoggerFactory.getLogger(MessageRedisReceiver.class);

    public void callSoundReceiver(String message) {
        logger.info("接收到的消息：{}", message);
    }
}
