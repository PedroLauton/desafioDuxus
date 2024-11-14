package br.com.duxusdesafio.dto;

/*
 * Classe para personalizar a resposta de erro durante o cadastro de integrantes e times.
 * Ela contém os campos de erro e mensagem que são retornados para o usuário, 
 * permitindo fornecer informações detalhadas sobre o motivo do erro.
 */

public class RespostaErroDTO {
	
	private String erro;
	private String mensagem;

	public RespostaErroDTO(String erro, String mensagem) {
		this.erro = erro;
		this.mensagem = mensagem;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
