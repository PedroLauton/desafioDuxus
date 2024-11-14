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
import br.com.duxusdesafio.service.exceptions.RecursoNaoEncontradoException;

@Service
public class ApiService {

    //Vai retornar um Time, com a composição do time daquela data.
    public Time timeDaData(LocalDate data, List<Time> todosOsTimes){
        for (Time time : todosOsTimes) {
			if(time.getData().isEqual(data)) {
				return time; 
			}
		}
       throw new RecursoNaoEncontradoException("Time não encontrado na data fornecida.");
    }

    //Vai retornar o integrante que estiver presente na maior quantidade de times dentro do período.
    public Integrante integranteMaisUsado(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
    	//Map para contabilizar a quantidade de times em que cada integrante aparece.
        Map<Integrante, Long> integrantesMap = new HashMap<>();

        //Filtra os times dentro do período fornecido e contabiliza a quantidade de vezes que cada integrante aparece.
        todosOsTimes.stream()
            .filter(time -> estaDentroDoPeriodo(time.getData(), dataInicial, dataFinal)) 
            .flatMap(time -> time.getComposicaoTime().stream()) 
            .map(compTime -> compTime.getIntegrante()) 
            .forEach(integrante -> 
                integrantesMap.put(integrante, integrantesMap.getOrDefault(integrante, 0L) + 1L)); 

        //Retorna o integrante mais usado ou lança exceção se não encontrar nenhum.
        return integrantesMap.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Integrante mais usado não encontrado. Verifique as datas de pesquisa."));
    }

    //Vai retornar uma lista com os nomes dos integrantes do time mais comum dentro do período
    public List<String> integrantesDoTimeMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
    	//Map para contar a quantidade de vezes que cada time aparece no período.
        Map<Time, Long> timeMap = new HashMap<>();

        //Filtra os times dentro do período e conta suas ocorrências.
        todosOsTimes.stream()
            .filter(time -> estaDentroDoPeriodo(time.getData(), dataInicial, dataFinal)) 
            .forEach(time -> timeMap.put(time, timeMap.getOrDefault(time, 0L) + 1L));

        //Identifica o time mais comum no período.
        Time timeMaisComum = timeMap.entrySet().stream()
            .max(Map.Entry.comparingByValue()) 
            .map(Map.Entry::getKey)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Nenhum time encontrado no intervalo de datas fornecido."));

        //Retorna os nomes dos integrantes do time mais comum.
        List<ComposicaoTime> listComposicaoTimeMaisComum = timeMaisComum.getComposicaoTime();
        return listComposicaoTimeMaisComum.stream()
            .map(compTime -> compTime.getIntegrante().getNome())
            .collect(Collectors.toList());
    }

    //Vai retornar a função mais comum nos times dentro do período
    public String funcaoMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
    	//Map para contar as funções mais frequentes entre os integrantes.
        Map<String, Long> funcaoMap = new HashMap<>();
        //Set para evitar a contagem duplicada de integrantes.
        Set<Integrante> integranteSet = new HashSet<>();

        //Filtra os times e agrega os integrantes no conjunto.
        todosOsTimes.stream()
            .filter(time -> estaDentroDoPeriodo(time.getData(), dataInicial, dataFinal))
            .flatMap(time -> time.getComposicaoTime().stream()) 
            .forEach(compTime -> integranteSet.add(compTime.getIntegrante()));

        //Conta a frequência de cada função.
        integranteSet.stream()
            .forEach(integrante -> funcaoMap.put(integrante.getFuncao(), 
                funcaoMap.getOrDefault(integrante.getFuncao(), 0L) + 1L));

        //Retorna a função mais comum ou lança uma exceção se não houver nenhuma.
        return funcaoMap.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Nenhuma função encontrada no intervalo de datas fornecido."));
    }

    //Vai retornar o nome da Franquia mais comum nos times dentro do período
    public String franquiaMaisFamosa(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
    	//Map para contar as franquias mais frequentes entre os integrantes.
        Map<String, Long> franquiaMap = new HashMap<>();
        //Set para evitar a contagem duplicada de integrantes.
        Set<Integrante> integranteSet = new HashSet<>();
        
        //Filtra os times e agrega os integrantes no conjunto.
        todosOsTimes.stream()
            .filter(time -> estaDentroDoPeriodo(time.getData(), dataInicial, dataFinal))
            .flatMap(time -> time.getComposicaoTime().stream()) 
            .forEach(compTime -> integranteSet.add(compTime.getIntegrante()));

        //Conta a frequência de cada franquia.
        integranteSet.stream()
            .forEach(integrante -> franquiaMap.put(integrante.getFranquia(), 
                franquiaMap.getOrDefault(integrante.getFranquia(), 0L) + 1L));

        //Retorna a franquia mais comum ou lança uma exceção se não houver nenhuma.
        return franquiaMap.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Nenhuma franquia mais famosa encontrada no intervalo de datas fornecido."));
    }


    //Vai retornar o número (quantidade) de Franquias dentro do período
    public Map<String, Long> contagemPorFranquia(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
    	//Map para contar as franquias dentro do período.
        Map<String, Long> quantidadeFranquiaMap = new HashMap<>();
        //Set para evitar a contagem duplicada de integrantes.
        Set<Integrante> integranteMap = new HashSet<>();

        //Filtra os times e agrega os integrantes no conjunto.
        todosOsTimes.stream()
            .filter(time -> estaDentroDoPeriodo(time.getData(), dataInicial, dataFinal))
            .flatMap(time -> time.getComposicaoTime().stream()) 
            .forEach(compTime -> integranteMap.add(compTime.getIntegrante()));

        //Conta a quantidade de franquias.
        integranteMap.stream()
            .forEach(integranteTime -> quantidadeFranquiaMap.put(integranteTime.getFranquia(), 
                quantidadeFranquiaMap.getOrDefault(integranteTime.getFranquia(), 0L) + 1L));

        //Lança uma exceção se não encontrar nenhuma franquia.
        if (quantidadeFranquiaMap.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhuma franquia encontrada no período fornecido.");
        }
        
        //Retorna a contagem por franquia.
        return quantidadeFranquiaMap;
    }

    //Vai retornar o número (quantidade) de Funções dentro do período
    public Map<String, Long> contagemPorFuncao(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
    	//Map para contar as funções dentro do período.
        Map<String, Long> quantidadeFuncaoMap = new HashMap<>();
        //Set para evitar a contagem duplicada de integrantes.
        Set<Integrante> integranteMap = new HashSet<>();

        //Filtra os times e agrega os integrantes no conjunto.
        todosOsTimes.stream()
            .filter(time -> estaDentroDoPeriodo(time.getData(), dataInicial, dataFinal))
            .flatMap(time -> time.getComposicaoTime().stream()) 
            .forEach(compTime -> integranteMap.add(compTime.getIntegrante()));

        //Conta a quantidade de funções.
        integranteMap.stream()
            .forEach(integrante -> quantidadeFuncaoMap.put(integrante.getFuncao(), 
                quantidadeFuncaoMap.getOrDefault(integrante.getFuncao(), 0L) + 1L));

        //Lança uma exceção se não encontrar nenhuma função.
        if (quantidadeFuncaoMap.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhuma função encontrada no período fornecido.");
        }
        
        //Retorna a contagem por função.
        return quantidadeFuncaoMap;
    }
    
    /*
     * Esse método privado retorna verdadeiro ou falso mediante a data dos times, auxiliando na filtragem dos times aderentes ao periodo. 
     */
    private boolean estaDentroDoPeriodo(LocalDate data, LocalDate dataInicial, LocalDate dataFinal) {
        if (dataInicial == null && dataFinal == null) {
        	return true;
        }
        
        return (data.isEqual(dataInicial) || data.isAfter(dataInicial)) &&
               (data.isEqual(dataFinal) || data.isBefore(dataFinal));
    }
}
