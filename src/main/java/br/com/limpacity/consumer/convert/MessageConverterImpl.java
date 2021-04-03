package br.com.limpacity.consumer.convert;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageConverterImpl implements MessageConverter {

    private final ObjectMapper mapper;

    public MessageConverterImpl(final ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> T convert(final Message message, final Class<T> targetClass) throws JsonProcessingException {
        final String body = new String(message.getBody());
        return mapper.readValue(body, targetClass);
    }
}
