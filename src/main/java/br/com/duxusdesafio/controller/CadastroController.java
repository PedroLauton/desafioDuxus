package br.com.duxusdesafio.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.service.ApiCadastroService;

/*
 * Esta classe é responsável por controlar as requisições HTTP e redirecionar os usuários para as páginas correspondentes.
 * Cada método mapeado por @GetMapping recebe uma URL e retorna o nome da view (tela) a ser renderizada pelo Spring MVC.
 */

@Controller
public class CadastroController {

	@Autowired
	private ApiCadastroService apiCadastroService; 
	
    @GetMapping("/")
    public String home() {
        return "index"; 
    }
    
    @GetMapping("/index")
    public String index() {
        return "index"; 
    }
    
    @GetMapping("/cadastroIntegrante")
    public String cadastroIntegrante() {
        return "cadastroIntegrante"; 
    }
    
    /*
     * Esse método envia os integrantes cadastrados e suas respectivas franquias para a tela de cadastro de times.
     * Os integrantes são enviados para que o usuário possa escolher quais membros farão parte dos times.
     * As franquias são utilizadas para filtrar os integrantes da mesma competição, garantindo que os times cadastrados 
     * tenham membros de franquias compatíveis, não permitindo o cadastro de times com integrantes de diferentes franquias.
     */
	@GetMapping("/cadastroTime")
	public String cadastroTime(Model model) {
		List<Integrante> integranteList = apiCadastroService.listarIntegrantes();
		Set<String> selecaoFranquiasSet = integranteList.stream()
				.map(integrante -> integrante.getFranquia())
				.collect(Collectors.toSet());
		
		model.addAttribute("integrantes", integranteList);
	    model.addAttribute("selecaoFranquias", selecaoFranquiasSet);
		return "cadastroTime";
	}
}