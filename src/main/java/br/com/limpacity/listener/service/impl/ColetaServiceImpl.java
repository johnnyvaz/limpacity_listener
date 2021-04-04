package br.com.limpacity.listener.service.impl;

import br.com.limpacity.listener.dto.SolicitaColetaUuidDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class ColetaServiceImpl {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.events.solicitaColeta.queueAguardando}")
    private String coletaAguardando;

    @Value("${spring.rabbitmq.events.solicitaColeta.queueAguardando}")
    private String notificarEmail;

    @Value("${spring.rabbitmq.events.solicitaColeta.queueAguardando}")
    private String notificarPush;

    @RabbitListener(queues="${spring.rabbitmq.events.solicitaColeta.queueAguardando}")
    public void processColeta(Message message) throws UnsupportedEncodingException, JsonProcessingException {

        String json = new String(message.getBody(), StandardCharsets.UTF_8);

        System.out.println("Mensagem recebida:"+json);

        ObjectMapper mapper = new ObjectMapper();

        SolicitaColetaUuidDTO solicitaColetaUuid = mapper.readValue(json , SolicitaColetaUuidDTO.class);

        // chamaria o api coleta

        rabbitTemplate.convertAndSend(notificarEmail, solicitaColetaUuid);
    }

}
