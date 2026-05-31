package com.tmevolut.ecommerce.api.controller;

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
}