package br.com.duxusdesafio.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * Esta classe configura o Spring Boot para usar arquivos .jsp como views.
 * O método configureViewResolvers indica o diretório "/WEB-INF/jsp/" onde os arquivos .jsp estão localizados
 * e também define a extensão ".jsp" para os arquivos de view.
 */

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/jsp/", ".jsp");
    }
}
