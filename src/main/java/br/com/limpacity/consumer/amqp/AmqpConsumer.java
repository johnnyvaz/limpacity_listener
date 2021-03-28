package br.com.limpacity.consumer.amqp;

import org.springframework.amqp.core.Message;

public interface AmqpConsumer {

	void consumer(Message message);
	
}
