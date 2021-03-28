package br.com.limpacity.consumer.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@SuperBuilder
public abstract class MessageDTO {

	private String messageType;
	private Date currentDate;

}
