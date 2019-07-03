package com.huachu.common.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;

import com.huachu.common.constants.RabbitExchange;
import com.huachu.common.constants.RabbitExchangeBinding;

@Configuration
public class RabbitBindingConfig implements BeanDefinitionRegistryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		Map<String, Queue> queueMap = new HashMap<>();
		Map<String, Binding> bindingMap = new HashMap<>();
		Binding binding = null;
		String queueName = null;
		String routingKey = null;
		Queue queue = null;
		RabbitExchangeBinding[] exchanges = RabbitExchangeBinding.values();
		if (exchanges != null && exchanges.length > 0) {
			TopicExchange topicExchange = new TopicExchange(RabbitExchange.TOPIC_EXCHANGE);
			DirectExchange directExchange = new DirectExchange(RabbitExchange.DIRECT_EXCHANGE);
			int i = 1;
			for (RabbitExchangeBinding rabbitExchange : exchanges) {
				queueName = rabbitExchange.getQueue();
				routingKey = rabbitExchange.getRoutingKey();
				if (!queueMap.containsKey(queueName)) {
					queue = new Queue(queueName);
					queueMap.put(queueName, queue);
				} else {
					queue = queueMap.get(queueName);
				}
				
				if (rabbitExchange.isDirectExchange()) {
					binding = BindingBuilder.bind(queue).to(directExchange).with(routingKey);
					bindingMap.put(RabbitExchange.DIRECT_EXCHANGE+(i++)+"Binding", binding);
				} else if (rabbitExchange.isTopicExchange()) {
					binding = BindingBuilder.bind(queue).to(topicExchange).with(routingKey);
					bindingMap.put(RabbitExchange.TOPIC_EXCHANGE+(i++)+"Binding", binding);
				}
			}
		}
		
		for(Entry<String, Binding> b : bindingMap.entrySet()) {
			beanFactory.registerSingleton(b.getKey(), b.getValue());
		}
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

	}

}
