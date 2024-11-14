package br.com.duxusdesafio.service.exceptions;

/*
 * Classe de exceção que é utilizada quando um recurso não foi encontrado no banco de dados.
 */

public class RecursoNaoEncontradoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public RecursoNaoEncontradoException(String message) {
		super(message);
	}

}
