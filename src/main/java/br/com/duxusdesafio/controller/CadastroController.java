package br.com.duxusdesafio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.duxusdesafio.service.ApiCadastroService;

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
    
    @GetMapping("/cadastroTime")
    public String cadastroTime(Model model) {
    	 model.addAttribute("integrantes", apiCadastroService.listarIntegrantes());
         return "cadastroTime";
    }
}