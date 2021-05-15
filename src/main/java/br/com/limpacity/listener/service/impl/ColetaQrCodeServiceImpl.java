package br.com.limpacity.listener.service.impl;

import br.com.limpacity.listener.dto.ColetaRetorno;
import br.com.limpacity.listener.dto.ColetaRetornoDTO;
import br.com.limpacity.producer.dto.SendColetaQrCodeDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
@Service
public class ColetaQrCodeServiceImpl {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.events.solicitaColeta.queueNotificarEmail}")
    private String notificarEmail;

    @Value("${limpacity.apiUrl}")
    private String apiUrl;


    @RabbitListener(queues="${spring.rabbitmq.events.solicitaColeta.queueAguardandoQrCode}")
    public void processColetaQrCode(Message message) throws UnsupportedEncodingException, JsonProcessingException {

        String json = new String(message.getBody(), StandardCharsets.UTF_8);
        System.out.println("Mensagem recebida: " + json);

        ObjectMapper mapper = new ObjectMapper();

        SendColetaQrCodeDTO sendColetaQrCode = mapper.readValue(json, SendColetaQrCodeDTO.class);

        System.out.println("sendColetaQrCode : " + sendColetaQrCode);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SendColetaQrCodeDTO> entity = new HttpEntity<>(sendColetaQrCode, headers);

        String link = apiUrl + "/qrcode/" + sendColetaQrCode.getPostoId();

        try {

            ResponseEntity<ColetaRetornoDTO> coletaRetorno = restTemplate.exchange(link, HttpMethod.POST, entity, ColetaRetornoDTO.class);
            rabbitTemplate.send(message);
            System.out.println("coletaRetorno : " + coletaRetorno.getStatusCode());


        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
                ColetaRetorno obj = mapper.readValue(ex.getResponseBodyAsString(), ColetaRetorno.class);
              //  return new ColetaRetorno(obj.getMensagem(), false);
            }
            throw ex;
        } catch (RuntimeException ex) {
            throw ex;
        }

    }

}
