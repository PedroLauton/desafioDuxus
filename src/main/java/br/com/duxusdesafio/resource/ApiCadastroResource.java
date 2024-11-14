package br.com.duxusdesafio.resource;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.duxusdesafio.dto.RespostaErroDTO;
import br.com.duxusdesafio.dto.RespostaSucessoDTO;
import br.com.duxusdesafio.dto.TimeDTO;
import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.service.ApiCadastroService;

/*
 * Controlador REST responsável pela criação e cadastro de integrantes e times.
 * Este controlador manipula as requisições recebidas do front-end, processa os dados 
 * e interage com o banco de dados através do serviço ApiCadastroService.
 */
@RestController
@RequestMapping(value = "/cadastro")
public class ApiCadastroResource {
	
	@Autowired
	private ApiCadastroService apiCadastroService; 
	
	
	/*
	 * Endpoint responsável por cadastrar um integrante.
	 * Quando uma requisição POST é recebida, o sistema espera um JSON contendo os dados do integrante.
	 * Esses dados são convertidos em um objeto Integrante e, em seguida, inseridos no banco de dados.
	 * Se a inserção for bem-sucedida, uma resposta com o status HTTP 201 (Criado) e uma mensagem de sucesso é retornada.
	 * Caso ocorra algum erro durante o processo, a resposta será um erro HTTP 500 (Erro Interno do Servidor), 
	 * juntamente com a mensagem de erro gerada.
	 */
	@PostMapping("/integrante")
	public ResponseEntity<?> inserirIntegrante(@RequestBody Integrante integrante){
		try {
			apiCadastroService.inserirIntegrante(integrante);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new RespostaSucessoDTO("Integrante cadastrado com sucesso!"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RespostaErroDTO("Erro ao cadastrar integrante", e.getMessage()));
		}
	}
	
	/*
     * Endpoint para cadastrar um time.
     * Recebe os dados de um time no formato JSON, processa as informações e cadastra o time
     * juntamente com os integrantes selecionados.
     * Se a incerção for bem sucedida, uma mensagem de sucesso é enviada para o front-end juntamente com o status HTTP 201.
     * Caso contrário, uma mensagem contendo o erro será enviada, juntamente com o status HTTP 500.
     * 
     */
	@PostMapping("/time")
	public ResponseEntity<?> cadastrarTime(@RequestBody TimeDTO timeDTO) {
	    try {
	        // Converte a data recebida
	        LocalDate data = LocalDate.parse(timeDTO.getData());

	        // Cria e insere o novo registro de Time
	        Time time = new Time();
	        time.setData(data);
	        apiCadastroService.inserirTime(time);

	        // Filtra os integrantes que foram selecionados no TimeDTO
	        List<Long> idsIntegrantesSelecionadosList = timeDTO.getIntegrantes().stream()
	            .map(IntegranteDTO -> IntegranteDTO.getId())
	            .collect(Collectors.toList());

	        // Busca apenas os integrantes selecionados no banco de dados
	        List<Integrante> integrantesSelecionadosList = apiCadastroService.listarIntegrantesPorId(idsIntegrantesSelecionadosList);

	        // Cria a composição do time somente com os integrantes selecionados
	        List<ComposicaoTime> composicaoTimeList = integrantesSelecionadosList.stream()
	            .map(integrante -> new ComposicaoTime(time, integrante))
	            .collect(Collectors.toList());

	        // Associa a composição ao time e insere no banco de dados
	        time.setComposicaoTime(composicaoTimeList);
	        apiCadastroService.inserirComposicaoTime(composicaoTimeList);

	        return ResponseEntity.status(HttpStatus.CREATED)
	                .body(new RespostaSucessoDTO("Time cadastrado com sucesso!"));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new RespostaErroDTO("Erro ao cadastrar time", e.getMessage()));
	    }
	}

}
