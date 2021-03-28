package br.com.limpacity.consumer.amqp.impl;

import br.com.limpacity.consumer.amqp.AmqpRePublish;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

@Slf4j
@Component
public class RePublishRabbitMQ implements AmqpRePublish {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Value("${spring.rabbitmq.request.exchange.name}")
	private String exchangeName;

	@Value("${spring.rabbitmq.request.queue.routing-key}")
	private String queueRoutingKey;

	@Value("${spring.rabbitmq.request.dead-letter.name}")
	private String deadLetterName;

	@Value("${spring.rabbitmq.request.parking-lot.routing-key}")
	private String parkingLotRoutingKey;
	
	@Value("${spring.rabbitmq.request.dead-letter.retry-quantity}")
	private String deadLetterRetryQuantity;

	private static final String X_RETRIES_HEADER = "x-retries";

	private static final Object X_DEATH_HEADER = "x-death";

	private static final String ROUTING_KEY = "routing-key";
	
	@Override
	@Scheduled(fixedDelayString = "${spring.rabbitmq.request.dead-letter.retry-delay}")
	public void rePublish() {
		try {
			this.receiveMessageDeadLetterQueue();      			
		} catch(Exception e) {
			RePublishRabbitMQ.log.error(e.getMessage());
		}
	}

	private void receiveMessageDeadLetterQueue() {
		Message message = this.rabbitTemplate.receive(this.deadLetterName);
		if (message != null) {
			Map<String, Object> headers = message.getMessageProperties().getHeaders();
			Integer retriesHeader = (Integer) headers.get(X_RETRIES_HEADER);
			log.info(headers.toString());

			String routingKey = getRoutingKey((ArrayList<?>) headers.get(X_DEATH_HEADER));

			String body = new String(message.getBody());
			RePublishRabbitMQ.log.info(String.format("message dead letter - %s : Received -> RetryQuantity - %d", body, retriesHeader));
			
			if (retriesHeader == null) {
				retriesHeader = 0;
			}
			if (retriesHeader < Integer.parseInt(this.deadLetterRetryQuantity)) {
				RePublishRabbitMQ.log.info(String.format("message dead letter - %s : Republished to main queue -> RetryQuantity - %d", body, retriesHeader));
				MDC.put(ROUTING_KEY, routingKey);
				headers.put(X_RETRIES_HEADER, retriesHeader + 1);
				this.rabbitTemplate.send(this.exchangeName, routingKey, message);
				
			} else {
				MDC.put(ROUTING_KEY, this.parkingLotRoutingKey);
				RePublishRabbitMQ.log.info(
						String.format("message dead letter - %s : Send to Parking Lot queue -> RetryQuantity - %d",
								body, retriesHeader));
				this.rabbitTemplate.send(this.exchangeName, this.parkingLotRoutingKey, message);
				
			}
			MDC.put(ROUTING_KEY, null);
		}
	}

	private String getRoutingKey(ArrayList<?> xdeath) {
		if (xdeath.isEmpty()) {
			return this.parkingLotRoutingKey;
		}
		
		Map<String, ArrayList<String>> deathProperties = (Map<String, ArrayList<String>>) xdeath.get(0);			
		if(deathProperties.containsKey("routing-keys"))
			return deathProperties.get("routing-keys").get(0);		
		else
			return this.parkingLotRoutingKey;
	}
}
