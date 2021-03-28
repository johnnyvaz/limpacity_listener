package br.com.limpacity.consumer.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class SolicitaColetaMessageDTO extends MessageDTO {

	private SolicitaBodyDTO loadRequest;

}
