package br.com.duxusdesafio.resource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.repositories.TimeRepository;
import br.com.duxusdesafio.service.ApiService;

/*
 * Controlador REST responsável pelo processamento de dados dos times.
 * Este controlador manipula as requisições e exibe informações detalhadas 
 * sobre os times, seus integrantes, funções e franquias com base em datas fornecidas.
 */

@RestController
@RequestMapping(value = "/processamento")
public class ApiProcessamentoResource {

	@Autowired
	private TimeRepository timeRepository; 
	
	@Autowired
	private ApiService apiService; 
	
	/*
     * Endpoint responsável por buscar um time específico com base na data fornecida.
     * A data é passada como um parâmetro na requisição HTTP e convertida para LocalDate.
     * O time correspondente à data fornecida é então buscado no banco de dados.
	 */
	@GetMapping("/TimeDaData")
	public ResponseEntity<Map<String, Object>> timeDaData(@RequestParam(required = false) String data) {
		
		//Recupera todos os times.
		List<Time> todosOsTimesList = timeRepository.findAll();
		//Converte a string para LocalDate.
	    LocalDate dataConvertida = converterData(data);
	    //Verifica se a data é válida.
	    verificacaoData(dataConvertida);
	    
	    //Envia os dados para ApiService, que retorna um time com a data especificada. 
	    Time timeEncontrado = apiService.timeDaData(dataConvertida, todosOsTimesList);
	    
	    //Coleta os nomes dos integrantes dos times para exibição. 
	    List<String> nomesIntegrantesList = timeEncontrado.getComposicaoTime().stream()
	             .map(compTime -> compTime.getIntegrante().getNome())
	             .collect(Collectors.toList());
	    
	    //Criação da estrutura Map para atribuir uma chave a data e integrantes do time, ajustando a visualização da requisição.
	    Map<String, Object> resposta = new HashMap<>();
	    resposta.put("Integrantes", nomesIntegrantesList);
	    resposta.put("Data", timeEncontrado.getData());

	    //Retorna o time para exibição.
	    return ResponseEntity.ok(resposta);
	}
	
	/*
     * Endpoint responsável por buscar a função mais comum entre os integrantes.
     * A data Inicial e data Final são opcionais, mas deve-se fornecer as duas ou nenhuma para o funcionamento da requisição.
     * A função mais comum é buscada no banco de dados e retornada para o usuário.
	 */
	@GetMapping("/FuncaoMaisComum")
	public ResponseEntity<Map<String, String>> funcaoMaisComum(
	        @RequestParam(required = false) String datainicial, 
	        @RequestParam(required = false) String datafinal) {
		
		//Recupera todos os times.
	    List<Time> todosOsTimesList = timeRepository.findAll();
	    //Converte as datas.
	    LocalDate dataInicialConvertida = converterData(datainicial);
	    LocalDate dataFinalConvertida = converterData(datafinal);
	    //Verifica se as datas são válidas, ou seja, se ambas são nulas ou não. 
	    verificacaoData(dataInicialConvertida, dataFinalConvertida);
	    
	    //Envia os dados para ApiService, que retorna a função mais comum de acordo com as datas, sejam elas nulas ou não. 
	    String funcaoMaisComum = apiService.funcaoMaisComum(dataInicialConvertida, dataFinalConvertida, todosOsTimesList);
	    
	    //Criação da estrutura Map para atribuir uma chave a função mais comum, ajustando a visualização da requisição.
	    Map<String, String> resposta = new HashMap<>();
	    resposta.put("Função", funcaoMaisComum);
	    //Retorna a função mais comum.
	    return ResponseEntity.ok(resposta);
	}
	
	/*
     * Endpoint responsável por buscar o integrante mais comum entre os times.
     * A data Inicial e data Final são opcionais, mas deve-se fornecer as duas ou nenhuma para o funcionamento da requisição.
     * O integrante mais comum é buscado no banco de dados e retornado para o usuário.
	 */
	@GetMapping("/IntegranteMaisUsado")
	public ResponseEntity<Map<String, String>> integranteMaisUsado(
	        @RequestParam(required = false) String datainicial, 
	        @RequestParam(required = false) String datafinal) {
		
		//Recupera todos os times.
	    List<Time> todosOsTimesList = timeRepository.findAll();
	    //Converte as datas.
	    LocalDate dataInicialConvertida = converterData(datainicial);
	    LocalDate dataFinalConvertida = converterData(datafinal);
	    //Verifica se as datas são válidas, ou seja, se ambas são nulas ou não. 
	    verificacaoData(dataInicialConvertida, dataFinalConvertida);
	    
	    //Envia os dados para ApiService, que retorna a função mais comum de acordo com as datas, sejam elas nulas ou não. 
	    Integrante integranteMaisUsado= apiService.integranteMaisUsado(dataInicialConvertida, dataFinalConvertida, todosOsTimesList);
	    
	    //Criação da estrutura Map para atribuir uma chave a função mais comum, ajustando a visualização da requisição.
	    Map<String, String> resposta = new HashMap<>();
	    resposta.put("Integrante", integranteMaisUsado.getNome());
	    //Retorna a função mais comum.
	    return ResponseEntity.ok(resposta);
	}
	
	/*
     * Endpoint responsável por encontrar os integrantes do time mais comum em um período de tempo.
     * A data Inicial e data Final são opcionais, mas deve-se fornecer as duas ou nenhuma para o funcionamento da requisição.
     * Os integrantes do time mais comum é buscado no banco de dados e retornado para o usuário.
	 */
	@GetMapping("/TimeMaisComum")
	public ResponseEntity<Map<String, List<String>>> timeMaisComum(
	        @RequestParam(required = false) String datainicial, 
	        @RequestParam(required = false) String datafinal) {

		//Recupera todos os times.
	    List<Time> todosOsTimesList = timeRepository.findAll();
	    //Verifica se as datas são válidas, ou seja, se ambas são nulas ou não. 
	    LocalDate dataInicialConvertida = converterData(datainicial);
	    LocalDate dataFinalConvertida = converterData(datafinal);
	    //Verifica se as datas são válidas.
	    verificacaoData(dataInicialConvertida, dataFinalConvertida);
	    
	    //Envia os dados para ApiService, que retorna os integrantes do time mais comum de acordo com as datas, sejam elas nulas ou não. 
	    List<String> integrantesDoTimeMaisComum = apiService.integrantesDoTimeMaisComum(dataInicialConvertida, dataFinalConvertida, todosOsTimesList);
	    
	    //Criação da estrutura Map para atribuir uma chave aos integrantes, ajustando a visualização da requisição.
	    Map<String, List<String>> resposta = new HashMap<>();
	    resposta.put("Integrantes", integrantesDoTimeMaisComum);
	    
	    //Retorna os integrantes do time mais comum. 
	    return ResponseEntity.ok(resposta);
	}
	
	/*
     * Endpoint responsável por encontrar a franquia mais famosa em um período de tempo.
     * A data Inicial e data Final são opcionais, mas deve-se fornecer as duas ou nenhuma para o funcionamento da requisição.
     * A franquia mais famosa é buscada no banco de dados e retornada para o usuário.
     * O funcionamento é semelhante aos métodos anteiores, por isso será suprimido comentários no método.
	 */
	@GetMapping("/FranquiaMaisFamosa")
	public ResponseEntity<Map<String, String>> franquiaMaisFamosa(
	        @RequestParam(required = false) String datainicial, 
	        @RequestParam(required = false) String datafinal) {

	    List<Time> todosOsTimes = timeRepository.findAll();
	    LocalDate dataInicialConvertida = converterData(datainicial);
	    LocalDate dataFinalConvertida = converterData(datafinal);
	    verificacaoData(dataInicialConvertida, dataFinalConvertida);

	    String franquiaMaisFamosa = apiService.franquiaMaisFamosa(dataInicialConvertida, dataFinalConvertida, todosOsTimes);

	    Map<String, String> resposta = new HashMap<>();
	    resposta.put("Franquia", franquiaMaisFamosa);

	    return ResponseEntity.ok(resposta);
	}
	
	/*
     * Endpoint responsável por retornar a contagem de integrantes por franquia em um período de tempo.
     * A data Inicial e data Final são opcionais, mas deve-se fornecer as duas ou nenhuma para o funcionamento da requisição.
     * A quantidade de integrantes por franquia é buscada no banco de dados e retornada para o usuário.
     * O funcionamento é semelhante aos métodos anteiores, por isso será suprimido comentários no método.
	 */
	@GetMapping("/ContagemPorFranquia")
	public ResponseEntity<Map<String, Long>> cotagemPorFranquia(
	        @RequestParam(required = false) String datainicial, 
	        @RequestParam(required = false) String datafinal) {

	    List<Time> todosOsTimes = timeRepository.findAll();
	    LocalDate dataInicialConvertida = converterData(datainicial);
	    LocalDate dataFinalConvertida = converterData(datafinal);
	    verificacaoData(dataInicialConvertida, dataFinalConvertida);

	    Map<String, Long> contagemPorFranquia = apiService.contagemPorFranquia(dataInicialConvertida, dataFinalConvertida, todosOsTimes);

	    return ResponseEntity.ok(contagemPorFranquia);
	}
	
	/*
     * Endpoint responsável por retornar a contagem de integrantes por função em um período de tempo.
     * A data Inicial e data Final são opcionais, mas deve-se fornecer as duas ou nenhuma para o funcionamento da requisição.
     * A quantidade de integrantes por função é buscada no banco de dados e retornada para o usuário.
     * O funcionamento é semelhante aos métodos anteiores, por isso será suprimido comentários no método.
	 */
	@GetMapping("/ContagemPorFuncao")
	public ResponseEntity<Map<String, Long>> cotagemPorFuncao(
	        @RequestParam(required = false) String datainicial, 
	        @RequestParam(required = false) String datafinal) {

	    List<Time> todosOsTimes = timeRepository.findAll();
	    LocalDate dataInicialConvertida = converterData(datainicial);
	    LocalDate dataFinalConvertida = converterData(datafinal);
	    verificacaoData(dataInicialConvertida, dataFinalConvertida);

	    Map<String, Long> contagemPorFuncao = apiService.contagemPorFuncao(dataInicialConvertida, dataFinalConvertida, todosOsTimes);

	    return ResponseEntity.ok(contagemPorFuncao);
	}
	
	
	/*
	 * Método privado que verifica se ambas as datas são nulas ou não, e se a data inicial é menor que a data final.
	 * Caso alguma das verificações seja verdadera, uma exceção é discriminando qual o erro. 
	 */
	private void verificacaoData(LocalDate dataInicial, LocalDate dataFinal) {
	    if (dataInicial == null ^ dataFinal == null) {
	        throw new IllegalArgumentException("Ambas as datas (Data Inicial e Data Final) devem ser fornecidas ou ambas devem ser nulas.");
	    }

	    if (dataInicial != null && dataFinal != null && dataInicial.isAfter(dataFinal)) {
			throw new IllegalArgumentException("A Data Inicial deve ser anterior à Data Final.");
	    }
	}
	
	/*
	 * Sobrecarga de método para o endpoint 'TimeDaData', que verifica apenas uma data. 
	 */
	private void verificacaoData(LocalDate data) {
	    if (data == null) {
	        throw new IllegalArgumentException("Uma data deve ser fornecida para buscar um time.");
	    }
	}
	
	/*
	 * Método privado que realiza a conversão de string para LocalDate. Caso o formato esteja incorreto, uma exceção é lançada. 
	 */
	private LocalDate converterData(String data) {
	    if (data != null) {
	        try {
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	            LocalDate dataConvertida = LocalDate.parse(data, formatter);
	            return dataConvertida;
	        } catch (DateTimeParseException e) {
	            throw new IllegalArgumentException("Formato de data inválido. O formato esperado é yyyy-MM-dd.");
	        }
	    }
	    return null; 
	}
}
