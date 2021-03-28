package br.com.limpacity.consumer.factory;

import br.com.limpacity.consumer.factory.events.ColetaEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventServiceFactory {

	@Autowired
    private List<ColetaEvent> coletaService;

	private final Map<String, ColetaEvent> eventMap = new HashMap<>();

	public ColetaServiceFactory(final List<ColetaEvent> coleta){
		this.coletaService = coleta;
	}



    @PostConstruct
	public void initMyServiceCache() {
		for (ColetaEvent event : eventServices) {
			eventMap.put(event.getEvent().getValue(), event);
		}
	}
    
    public ColetaEvent getEvent(String key, String body){
    	
    	key = getAnotherKeyIfIsRequeueMessage(key, body);

		ColetaEvent eventService = eventMap.get(key);
    	if (eventService==null) {
    		throw new UnsupportedOperationException("Event doesn't implement for routing.key " + key);
    	}
    	return eventService;
    	
    }

	private String getAnotherKeyIfIsRequeueMessage(String key, String body) {
		if(key.equalsIgnoreCase("solicita-coleta")) {
    		for (ColetaEvent event : eventServices) {
    			key = Boolean.TRUE.equals(body.contains(event.getEvent().getValue())) ?
    					event.getEvent().getValue() : key; 
    		}
    	}
		return key;
	}
    
	
}
