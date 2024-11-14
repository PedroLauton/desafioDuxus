package br.com.duxusdesafio.dto;

import java.util.List;

import br.com.duxusdesafio.model.Integrante;

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
