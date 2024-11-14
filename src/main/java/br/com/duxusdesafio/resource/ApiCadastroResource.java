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

@RestController
@RequestMapping(value = "/cadastro")
public class ApiCadastroResource {
	
	@Autowired
	private ApiCadastroService apiCadastroService; 
	
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
	
	@PostMapping("/time")
	public ResponseEntity<?> cadastrarTime(@RequestBody TimeDTO timeDTO) {
	    try {
	        LocalDate data = LocalDate.parse(timeDTO.getData());

	        Time time = new Time();
	        time.setData(data);

	        List<Integrante> integrantes = apiCadastroService.listarIntegrantes();
	        
	        apiCadastroService.inserirTime(time);  

	        List<ComposicaoTime> composicaoTime = integrantes.stream()
	            .map(integrante -> new ComposicaoTime(time, integrante))
	            .collect(Collectors.toList());

	        time.setComposicaoTime(composicaoTime);
	        apiCadastroService.inserirTime(time);

	        return ResponseEntity.status(HttpStatus.CREATED)
	                .body(new RespostaSucessoDTO("Time cadastrado com sucesso!"));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new RespostaErroDTO("Erro ao cadastrar time", e.getMessage()));
	    }
	}
}
