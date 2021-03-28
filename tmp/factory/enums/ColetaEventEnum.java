package br.com.limpacity.consumer.factory.enums;

public enum ColetaEventEnum {

	COLETA_SAVED("coleta.task-saved");
	
	private String value;	

	private ColetaEventEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
