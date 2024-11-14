package br.com.duxusdesafio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.repositories.ComposicaoTimeRepository;
import br.com.duxusdesafio.repositories.IntegranteRepository;
import br.com.duxusdesafio.repositories.TimeRepository;

/*
 * Serviço de cadastro responsável pela comunicação com os repositórios
 * e pela realização das operações de inserção de dados no banco de dados.
 */

@Service
public class ApiCadastroService {
	
	@Autowired
	private IntegranteRepository integranteRepository;
	
	@Autowired
	private TimeRepository timeRepository;
	
	@Autowired
	private ComposicaoTimeRepository composicaoTimeRepository;
	
	
	public Integrante inserirIntegrante(Integrante integrante) {
		return integranteRepository.save(integrante);
	}
	
	public List<Integrante> listarIntegrantes() {
		return integranteRepository.findAll();
	}
	
	public List<Integrante> listarIntegrantesPorId(List<Long> ids) {
		return integranteRepository.findAllById(ids);
	}
	
	public Time inserirTime(Time time) {
		return timeRepository.save(time);
	}
	
	public List<ComposicaoTime> inserirComposicaoTime(List<ComposicaoTime> composicaoTime) {
		return composicaoTimeRepository.saveAll(composicaoTime);
	}
}
