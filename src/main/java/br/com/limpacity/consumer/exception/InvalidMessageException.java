package br.com.limpacity.consumer.exception;

public class InvalidMessageException extends Exception {

	private static final long serialVersionUID = -6977503072587904629L;
	
	public InvalidMessageException(String message) {
		super(String.format("Invalid Message unexpected exception: ### %s", message));
	}

}
