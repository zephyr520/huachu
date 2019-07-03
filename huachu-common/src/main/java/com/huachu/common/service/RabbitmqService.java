package com.huachu.common.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.huachu.common.constants.RabbitExchangeBinding;
import com.huachu.common.util.LDTUtil;
import com.huachu.common.util.MD5Util;

@Service
public class RabbitmqService {

	private final static Logger LOGGER = LoggerFactory.getLogger(RabbitmqService.class);
	
	private final static String RABBIT_MQ_MSG_KEY_PREFIX = "MQ:message:";
	private final static String ERR_MQ_MSG_KEY_PREFIX = "MQ:error:";
	
	@Autowired
    private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private RedisService redisService;
	
	public void send(RabbitExchangeBinding binding, Object obj) {
		if(binding == null) {
			LOGGER.error("RabbitExchangeBinding is null, 数据错误.");
    		return;
    	}
		LOGGER.info("Sending mq message, RoutingKey={}, message={}", binding.getRoutingKey(), JSON.toJSONString(obj));
		try {
        	String correlationId = storeMsgInRedis(binding, obj, true);
    		rabbitTemplate.convertAndSend(binding.getExchange(), binding.getRoutingKey(), obj, new CorrelationData(correlationId));
		}catch(Exception e) {
			LOGGER.error("异常",e);
			LOGGER.error("Send MQ message failed. Exchange={}, RoutingKey={}, message={}, errorMessage={}", binding.getExchange(), binding.getRoutingKey(), obj, e.getMessage());
		}
	}
	
	public String storeMsgInRedis(RabbitExchangeBinding binding, Object obj, boolean isInit) throws Exception {
    	String dataToStr = JSON.toJSONString(obj, SerializerFeature.UseSingleQuotes, SerializerFeature.SortField, SerializerFeature.MapSortField);
    	String correlationId = MD5Util.md5(dataToStr, RABBIT_MQ_MSG_KEY_PREFIX);
    	if(isInit) {
    		correlationId = "INIT_"+correlationId;
    	}
    	@SuppressWarnings("unchecked")
		Map<String, Object> stored = redisService.get(RABBIT_MQ_MSG_KEY_PREFIX+correlationId, Map.class);
    	if(stored == null) {
    		
    		stored = new HashMap<>();
    		stored.put("correlationId", correlationId);
    		stored.put("exchange", binding.getExchange());
    		stored.put("routingKey", binding.getRoutingKey());
    		stored.put("createTime", LDTUtil.getDateTimeStr());
    		stored.put("retryCnt", 0);
    		stored.put("updateTime", LDTUtil.getDateTimeStr());
    		stored.put("data", obj);
    		redisService.set(RABBIT_MQ_MSG_KEY_PREFIX+correlationId, stored);
    	}
    	return correlationId;
    }
	
	public void removeRedisMsg(String correlationId) {
    	redisService.removeString(RABBIT_MQ_MSG_KEY_PREFIX+correlationId);
    }
    
    public void removeRedisMsg(Object obj, boolean isInit) {
    	String dataToStr = JSON.toJSONString(obj, SerializerFeature.UseSingleQuotes, SerializerFeature.SortField, SerializerFeature.MapSortField);
    	String correlationId;
		try {
			correlationId = MD5Util.md5(dataToStr, RABBIT_MQ_MSG_KEY_PREFIX);
			if(isInit) {
				correlationId = "INIT_"+correlationId;
			}
			redisService.removeString(RABBIT_MQ_MSG_KEY_PREFIX+correlationId);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
