//package br.com.limpacity.listener.service.impl;
//
//import br.com.limpacity.listener.dto.ColetaRetorno;
//import br.com.limpacity.listener.dto.ColetaRetornoDTO;
//import br.com.limpacity.producer.dto.SolicitaColetaUuidDTO;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.UnsupportedEncodingException;
//import java.nio.charset.StandardCharsets;
//import java.util.Objects;
//
//@Slf4j
//@Service
//public class ColetaServiceImpl {
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    @Value("${spring.rabbitmq.events.solicitaColeta.queueNotificarEmail}")
//    private String notificarEmail;
//
//    @Value("${spring.rabbitmq.events.solicitaColeta.queueNotificarPush}")
//    private String notificarPush;
//
//    @Value("${limpacity.apiUrl}")
//    private String apiUrl;
//
//    @RabbitListener(queues="${spring.rabbitmq.events.solicitaColeta.queueAguardando}")
//    public ColetaRetorno processColeta(Message message) throws UnsupportedEncodingException, JsonProcessingException {
//
//        String json = new String(message.getBody(), StandardCharsets.UTF_8);
//        System.out.println("Mensagem recebida: " + json);
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        SolicitaColetaUuidDTO solicitaColetaUuid = mapper.readValue(json, SolicitaColetaUuidDTO.class);
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<SolicitaColetaUuidDTO> entity = new HttpEntity<SolicitaColetaUuidDTO>(
//                solicitaColetaUuid, headers);
//
//        try {
//            ResponseEntity<ColetaRetornoDTO> coletaRetorno = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, ColetaRetornoDTO.class);
//            rabbitTemplate.convertAndSend(notificarEmail, solicitaColetaUuid);
//            rabbitTemplate.convertAndSend(notificarPush, solicitaColetaUuid);
//            return new ColetaRetorno(Objects.requireNonNull(coletaRetorno.getBody()).getMensagem(), true);
//        } catch (HttpClientErrorException ex) {
//            if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
//                //ObjectMapper mapper = new ObjectMapper();
//                ColetaRetorno obj = mapper.readValue(ex.getResponseBodyAsString(), ColetaRetorno.class);
//                return new ColetaRetorno(obj.getMensagem(), false);
//            }
//            throw ex;
//        } catch (RuntimeException ex) {
//            throw ex;
//        }
//
//    }
//
//}
