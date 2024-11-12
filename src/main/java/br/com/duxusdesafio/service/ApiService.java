package br.com.duxusdesafio.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.service.exceptions.ResourceNotFoundException;

@Service
public class ApiService {

    //Vai retornar um Time, com a composição do time daquela data
    public Time timeDaData(LocalDate data, List<Time> todosOsTimes){
        for (Time time : todosOsTimes) {
			if(time.getData().isEqual(data)) {
				return time; 
			}
		}
       throw new ResourceNotFoundException("Time não encontrado na data", data);
    }

    //Vai retornar o integrante que estiver presente na maior quantidade de times dentro do período
    public Integrante integranteMaisUsado(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        Map<Integrante, Long> integrantesMap = new HashMap<>();

        todosOsTimes.stream()
        .filter(time -> estaDentroDoPeriodo(time.getData(), dataInicial, dataFinal)) 
        .flatMap(time -> time.getComposicaoTime().stream()) 
        .map(ComposicaoTime::getIntegrante) 
        .forEach(integrante -> 
            integrantesMap.put(integrante, integrantesMap.getOrDefault(integrante, 0L) + 1L)); 


        return integrantesMap.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElseThrow(() -> new ResourceNotFoundException("Integrante mais usado não encontrado. Verifique as datas de pesquisa."));
    }

    //Vai retornar uma lista com os nomes dos integrantes do time mais comum dentro do período
    public List<String> integrantesDoTimeMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
        Map<Time, Long> timeMap = new HashMap<>();

        todosOsTimes.stream()
            .filter(time -> estaDentroDoPeriodo(time.getData(), dataInicial, dataFinal)) 
            .forEach(time -> timeMap.put(time, timeMap.getOrDefault(time, 0L) + 1L));
        
        Time timeMaisComum = timeMap.entrySet().stream()
            .max(Map.Entry.comparingByValue()) 
            .map(Map.Entry::getKey)
            .orElseThrow(() -> new ResourceNotFoundException("Nenhum time encontrado no intervalo de datas fornecido."));

        List<ComposicaoTime> listComposicaoTimeMaisComum = timeMaisComum.getComposicaoTime();

        return listComposicaoTimeMaisComum.stream()
            .map(compTime -> compTime.getIntegrante().getNome())
            .collect(Collectors.toList());
    }

    //Vai retornar a função mais comum nos times dentro do período
    public String funcaoMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
    	Map<String, Long> funcaoMap = new HashMap<>();
    	Set<Integrante> integranteSet = new HashSet<>();
    	
        todosOsTimes.stream()
            .filter(time -> estaDentroDoPeriodo(time.getData(), dataInicial, dataFinal))
            .flatMap(time -> time.getComposicaoTime().stream()) 
            .forEach(compTime -> integranteSet.add(compTime.getIntegrante()));
    	
        integranteSet.stream()
            .forEach(integrante -> funcaoMap.put(integrante.getFuncao(), 
            		funcaoMap.getOrDefault(integrante.getFuncao(), 0L) + 1L));

        return funcaoMap.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElseThrow(() -> new ResourceNotFoundException("Nenhuma função encontrada no intervalo de datas fornecido."));
    }

    //Vai retornar o nome da Franquia mais comum nos times dentro do período
    public String franquiaMaisFamosa(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
    	Map<String, Long> franquiaMap = new HashMap<>();
    	Set<Integrante> integranteSet = new HashSet<>();

        todosOsTimes.stream()
            .filter(time -> estaDentroDoPeriodo(time.getData(), dataInicial, dataFinal))
            .flatMap(time -> time.getComposicaoTime().stream()) 
            .forEach(compTime -> integranteSet.add(compTime.getIntegrante()));
	
        integranteSet.stream()
        	.forEach(integrante -> franquiaMap.put(integrante.getFranquia(), 
        			franquiaMap.getOrDefault(integrante.getFranquia(), 0L) + 1L));

        return franquiaMap.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElseThrow(() -> new ResourceNotFoundException("Nenhuma franquia mais famosa encontrada no intervalo de datas fornecido."));
    }


    //Vai retornar o número (quantidade) de Franquias dentro do período
    public Map<String, Long> contagemPorFranquia(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
    	Map<String, Long> quantidadeFranquiaMap = new HashMap<>();
    	Set<Integrante> integranteMap = new HashSet<>();
    	
        todosOsTimes.stream()
            .filter(time -> estaDentroDoPeriodo(time.getData(), dataInicial, dataFinal))
            .flatMap(time -> time.getComposicaoTime().stream()) 
            .forEach(compTime -> integranteMap.add(compTime.getIntegrante()));
            
        integranteMap.stream()
            	.forEach(integranteTime -> quantidadeFranquiaMap.put(integranteTime.getFranquia(), 
            			quantidadeFranquiaMap.getOrDefault(integranteTime.getFranquia(), 0L) + 1L));

        if (quantidadeFranquiaMap.isEmpty()) {
            throw new ResourceNotFoundException("Nenhuma franquia encontrada no período fornecido.");
        }
        
        return quantidadeFranquiaMap;
    }

    //Vai retornar o número (quantidade) de Funções dentro do período
    public Map<String, Long> contagemPorFuncao(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
    	Map<String, Long> quantidadeFuncaoMap = new HashMap<>();
    	Set<Integrante> integranteMap = new HashSet<>();
    	
        todosOsTimes.stream()
            .filter(time -> estaDentroDoPeriodo(time.getData(), dataInicial, dataFinal))
            .flatMap(time -> time.getComposicaoTime().stream()) 
            .forEach(compTime -> integranteMap.add(compTime.getIntegrante()));
        
        integranteMap.stream()
        	.forEach(integrante -> quantidadeFuncaoMap.put(integrante.getFuncao(), 
        			quantidadeFuncaoMap.getOrDefault(integrante.getFuncao(), 0L) + 1L));
        	
        if (quantidadeFuncaoMap.isEmpty()) {
            throw new ResourceNotFoundException("Nenhuma função encontrada no período fornecido.");
        }
        
        return quantidadeFuncaoMap;
    }
    
    private boolean estaDentroDoPeriodo(LocalDate data, LocalDate dataInicial, LocalDate dataFinal) {
        if (dataInicial == null && dataFinal == null) {
        	return true;
        }
        
        return (data.isEqual(dataInicial) || data.isAfter(dataInicial)) &&
               (data.isEqual(dataFinal) || data.isBefore(dataFinal));
    }
}
