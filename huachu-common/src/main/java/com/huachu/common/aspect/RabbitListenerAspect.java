package com.huachu.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.huachu.common.constants.RabbitExchangeBinding;
import com.huachu.common.service.RabbitmqService;

@Component
@Aspect
public class RabbitListenerAspect {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitListenerAspect.class);
	
	@Autowired
	private RabbitmqService rabbitmqService;
	
	@Around(value = "@annotation(org.springframework.amqp.rabbit.annotation.RabbitListener)")
	@Order(0)
	public Object listener(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		RabbitListener annotaion = signature.getMethod().getAnnotation(RabbitListener.class);
		String[] queues = annotaion.queues();
		String defaultQueueName = null;
		if (queues != null && queues.length > 0) {
			defaultQueueName = queues[0];
		}
		Object[] args = joinPoint.getArgs();
		Object msgData = null;
		if (args != null && args.length > 0) {
			msgData = args[0];
		}
		try {
			result = joinPoint.proceed(args);
			rabbitmqService.removeRedisMsg(msgData, false);
		} catch (Exception e) {
			LOGGER.error("监听消息队列发送异常", e);
			rabbitmqService.storeMsgInRedis(RabbitExchangeBinding.getByQueueName(defaultQueueName), msgData, false);
			LOGGER.error("Rabbit Listener 业务处理失败 已保存至Redis, queueName={}, msgData={}", defaultQueueName, msgData);
		}
		
		return result;
	}
}
