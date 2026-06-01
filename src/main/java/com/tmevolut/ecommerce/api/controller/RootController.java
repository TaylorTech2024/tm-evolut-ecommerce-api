package com.tmevolut.ecommerce.api.controller;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import io.swagger.v3.oas.annotations.Operation;

@Controller
// Utiliza @Controller comum (e não @RestController) para,
//permitir o redirecionamento HTTP de páginas.
public class RootController {

    @Operation(hidden = true) // Oculta este endpoint interno da própria listagem do Swagger
    @GetMapping("/")
    public String redirecionarParaSwagger() {
        return "redirect:/swagger-ui.html";
    }

    @Bean
public OpenAPI customOpenAPI() {

        return new OpenAPI().info(new Info()
                .title("TM-Evolut-Ecommerce")
                .version("1.0.0")
                .description("TM-Evolut-Ecommerce")
        );
    }
}