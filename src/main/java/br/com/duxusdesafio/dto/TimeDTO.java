package br.com.duxusdesafio.dto;

import java.util.List;

import br.com.duxusdesafio.model.Integrante;

/*
 * Classe responsável por transferir os dados necessários para o cadastro de um time,
 * entre o front-end e o back-end. Ela contém os dados da data do time e a lista de 
 * integrantes que farão parte do time. 
 * 
 * É utilizada na comunicação com o back-end, onde a data será convertida para o tipo
 * LocalDate e os integrantes serão mapeados para persistência no banco de dados.
 */

public class TimeDTO {
	
	private String data;
	private List<Integrante> integrantes;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public List<Integrante> getIntegrantes() {
		return integrantes;
	}

	public void setIntegrantes(List<Integrante> integrantes) {
		this.integrantes = integrantes;
	}
}
