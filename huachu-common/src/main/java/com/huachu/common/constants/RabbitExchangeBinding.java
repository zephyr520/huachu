package com.huachu.common.constants;

public enum RabbitExchangeBinding {

	USER_OPERATE_BEHAVIOR_LOG(RabbitExchange.DIRECT_EXCHANGE, RabbitQueue.COM_HC_Q_USER_OPERATE_BEHAVIOR_LOG)
	;
	private String exchange;
	private String routingKey;
	private String queue;

	private RabbitExchangeBinding(String exchange, String queue) {
		this.exchange = exchange;
		this.routingKey = queue;
		this.queue = queue;
	}

	public String getExchange() {
		return exchange;
	}

	public String getRoutingKey() {
		return routingKey;
	}

	public String getQueue() {
		return queue;
	}
	
	public boolean isDirectExchange() {
		return RabbitExchange.DIRECT_EXCHANGE.equals(exchange);
	}
	
	public boolean isTopicExchange() {
		return RabbitExchange.TOPIC_EXCHANGE.equals(exchange);
	}
	
	public static RabbitExchangeBinding getByName(String name) {
		for(RabbitExchangeBinding binding : values()) {
			if(String.valueOf(binding.name().trim()).equals(name.toUpperCase().trim())) {
				return binding;
			}
		}
		return null;
			
	}
	
	public static RabbitExchangeBinding getByQueueName(String queueName) {
		for(RabbitExchangeBinding binding : values()) {
			if(String.valueOf(binding.getQueue().toUpperCase()).equals(queueName.toUpperCase().trim())) {
				return binding;
			}
		}
		return null;
			
	}
}
