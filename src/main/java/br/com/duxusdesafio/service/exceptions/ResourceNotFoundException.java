package br.com.duxusdesafio.service.exceptions;

import java.time.LocalDate;

public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(String message, LocalDate data) {
		super(message + ": " + data);
	}
	
	public ResourceNotFoundException(String message) {
		super(message);
	}

}
