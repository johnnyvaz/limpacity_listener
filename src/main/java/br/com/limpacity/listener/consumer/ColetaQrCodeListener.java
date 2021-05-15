package br.com.limpacity.listener.consumer;

import com.tradeshift.amqp.annotation.EnableRabbitRetryAndDlq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.nio.charset.Charset;

public class ColetaQrCodeListener {

private static final Logger log = LoggerFactory.getLogger(ColetaQrCodeListener.class);

    @RabbitListener(containerFactory = "queueAguardandoQrCode", queues="${spring.rabbitmq.events.solicitaColeta.queueAguardandoQrCode}")
    @EnableRabbitRetryAndDlq(event = "queueAguardandoQrCode")
    public void onMessage(Message message) {
        process(message);
    }

//    @RabbitListener(containerFactory = "another-event", queues = "${spring.rabbitmq.custom.another-event.queue}")
//    @EnableRabbitRetryAndDlq(event = "another-event", retryWhen = { IllegalArgumentException.class, RuntimeException.class })
//    public void onMessageAnotherListener(Message message) {
//        process(message);
//    }

    private void process(Message message) {
        String messageStr = new String(message.getBody(), Charset.defaultCharset());

        if ("dlq".equals(messageStr)) {
            throw new RuntimeException("to dead-letter");
        }

        log.info("message = [{}]", messageStr);
    }

    }

