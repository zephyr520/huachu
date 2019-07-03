package com.huachu.api.mq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.huachu.common.constants.RabbitQueue;

@Component
public class TestListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TestListener.class);

	@RabbitListener(queues = RabbitQueue.COM_HC_Q_USER_OPERATE_BEHAVIOR_LOG)
	public void test(@RequestBody String receiveMsg) {
		LOGGER.info("从rabbitmq中接收到的消息是：{}", receiveMsg);
	}
}
