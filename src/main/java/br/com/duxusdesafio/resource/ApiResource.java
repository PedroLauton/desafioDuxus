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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.repositories.TimeRepository;
import br.com.duxusdesafio.response.TimeResponse;
import br.com.duxusdesafio.service.ApiService;

@RestController
@RequestMapping(value = "/processamento")
public class ApiResource {

	@Autowired
	private TimeRepository timeRepository; 
	
	@Autowired
	private ApiService apiService; 
	
	@GetMapping("/TimeDaData/{data}")
	public ResponseEntity<TimeResponse> timeDaData(@PathVariable String data) {
	    
		List<Time> todosOsTimes = timeRepository.findAll();
	    LocalDate dataConvertida = converterData(data);
	    Time timeEncontrado = apiService.timeDaData(dataConvertida, todosOsTimes);
	    
	    List<String> nomesIntegrantes = timeEncontrado.getComposicaoTime().stream()
	             .map(compTime -> compTime.getIntegrante().getNome())
	             .collect(Collectors.toList());

	    TimeResponse resposta = new TimeResponse(timeEncontrado.getData(), nomesIntegrantes);
	    return ResponseEntity.ok(resposta);
	}
	
	@GetMapping("/FuncaoMaisComum")
	public ResponseEntity<Map<String, String>> funcaoMaisComum(
	        @RequestParam(required = false) String datainicial, 
	        @RequestParam(required = false) String datafinal) {

	    List<Time> todosOsTimes = timeRepository.findAll();
	    LocalDate dataInicialConvertida = converterData(datainicial);
	    LocalDate dataFinalConvertida = converterData(datafinal);
	    verificacaoData(dataInicialConvertida, dataFinalConvertida);
	    
	    String funcaoMaisComum = apiService.funcaoMaisComum(dataInicialConvertida, dataFinalConvertida, todosOsTimes);

	    Map<String, String> resposta = new HashMap<>();
	    resposta.put("Função", funcaoMaisComum);

	    return ResponseEntity.ok(resposta);
	}
	
	@GetMapping("/TimeMaisComum")
	public ResponseEntity<Map<String, List<String>>> timeMaisComum(
	        @RequestParam(required = false) String datainicial, 
	        @RequestParam(required = false) String datafinal) {

	    List<Time> todosOsTimes = timeRepository.findAll();
	    LocalDate dataInicialConvertida = converterData(datainicial);
	    LocalDate dataFinalConvertida = converterData(datafinal);
	    verificacaoData(dataInicialConvertida, dataFinalConvertida);

	    List<String> integrantesDoTimeMaisComum = apiService.integrantesDoTimeMaisComum(dataInicialConvertida, dataFinalConvertida, todosOsTimes);

	    Map<String, List<String>> resposta = new HashMap<>();
	    resposta.put("Integrantes", integrantesDoTimeMaisComum);

	    return ResponseEntity.ok(resposta);
	}
	
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
	
	private void verificacaoData(LocalDate dataInicial, LocalDate dataFinal) {
		if(dataInicial == null || dataFinal == null) {
			throw new IllegalArgumentException("Deve ser fornecido a Data Inicial e a Data Final");
		}
		if(dataInicial.isAfter(dataFinal)) {
			throw new IllegalArgumentException("A data Inicial deve ser anterior a data Final.");
		}
	}
	
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
