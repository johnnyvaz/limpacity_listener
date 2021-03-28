package br.com.limpacity.consumer.factory.events;

import br.com.limpacity.consumer.factory.enums.ColetaEventEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.amqp.core.Message;

public interface ColetaEvent {

	ColetaEventEnum getEvent();
	void process(Message message) throws JsonProcessingException;
	
}
