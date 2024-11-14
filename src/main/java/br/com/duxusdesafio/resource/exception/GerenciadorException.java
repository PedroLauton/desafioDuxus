package br.com.duxusdesafio.resource.exception;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.duxusdesafio.service.exceptions.RecursoNaoEncontradoException;

/*
 * A classe GerenciadorException é responsável pelo gerenciamento global de exceções
 * lançadas nos endpoints da aplicação. 
 */

@ComponentScan
@ControllerAdvice
public class GerenciadorException {

	 /*
     * Método que lida com a exceção RecursoNaoEncontradoException, que é lançada
     * quando um recurso solicitado não é encontrado no sistema.
     * Esse método captura a exceção, cria um objeto de erro com as informações 
     * necessárias e retorna uma resposta HTTP com status 404 (NOT_FOUND).
     */
	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public ResponseEntity<ErroPadrao> resourceNotFound(RecursoNaoEncontradoException e, HttpServletRequest request){
		String error = "Recurso não encontrado.";
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErroPadrao err = new ErroPadrao(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	/*
     * Método que lida com a exceção IllegalArgumentException, que é lançada quando
     * um argumento inválido é fornecido em algum endpoint.
     * Este método captura a exceção e retorna uma resposta HTTP com status 400 (BAD_REQUEST).
     */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErroPadrao> resourceNotFound(IllegalArgumentException e, HttpServletRequest request){
		String error = "Argumento Ilegal.";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ErroPadrao err = new ErroPadrao(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
}
