package br.com.limpacity.consumer.amqp.impl;

import br.com.limpacity.consumer.amqp.AmqpConsumer;
import br.com.limpacity.consumer.factory.EventServiceFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RabbitMQConsumer implements AmqpConsumer {

	@Autowired
	EventServiceFactory factory;

	@Override
	@RabbitListener(queues = "${spring.rabbitmq.request.queue.name}")
	public void consumer(Message message) {
		String body = new String(message.getBody());
		RabbitMQConsumer.log.info("message received - {}" + body);
				
		try {
			factory.getEvent(message.getMessageProperties()
					.getReceivedRoutingKey(), body).process(body);
		} catch(Exception e) {
			log.error(e.getMessage());
			throw new AmqpRejectAndDontRequeueException(e.getMessage());
		}

	}

}
