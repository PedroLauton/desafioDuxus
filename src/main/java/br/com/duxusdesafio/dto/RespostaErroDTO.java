package br.com.duxusdesafio.dto;

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

	public String getmensagem() {
		return mensagem;
	}

	public void setmensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
