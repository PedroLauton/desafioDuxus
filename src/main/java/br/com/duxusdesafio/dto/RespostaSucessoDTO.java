package br.com.duxusdesafio.dto;

/*
 * Classe para personalizar a resposta de sucesso durante o cadastro de integrantes e times.
 * Ela contém o campo de mensagem que informa ao usuário o sucesos de cadastro.
 */

public class RespostaSucessoDTO {
	
	private String mensagem;

	public RespostaSucessoDTO(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
