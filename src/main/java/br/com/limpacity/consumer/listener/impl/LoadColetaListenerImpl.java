package br.com.limpacity.consumer.listener.impl;

import br.com.limpacity.consumer.dto.SolicitaColetaDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@RabbitListener
public class LoadColetaListenerImpl {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.events.solicitaColeta.exchange}")
    private String exchangeName;

    @Value("${spring.rabbitmq.events.solicitaColeta.coletaProcessando}")
    private String coletaProcessando;

//    private final MessageConverter converter;

//    private ColetaService service;

//    public LoadColetaListenerImpl(MessageConverter converter) {
//        this.converter = converter;
//    }

    @RabbitListener(queues = "${spring.rabbitmq.events.solicitaColeta.routingkey}")
    public void consumer(final Message message) throws JsonProcessingException {
        log.info("Starting consume message, message={}", message);
        rabbitTemplate.convertAndSend(exchangeName, coletaProcessando);
//        final LoadProcessedMessageDTO loadProcessedMessageDTO = converter
//                .convert(message, LoadProcessedMessageDTO.class);
        //service.process(loadProcessedMessageDTO);
        log.info(" *** COLETANDO *** , message={}", message);
        SolicitaColetaDTO col = SolicitaColetaDTO.builder().id("str01").build();
//        service.saveColeta(col);
    }
}
