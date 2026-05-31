
package com.tmevolut.ecommerce.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmevolut.ecommerce.api.dto.CategoriaRequest;
import com.tmevolut.ecommerce.api.dto.CategoriaResponse;
import com.tmevolut.ecommerce.api.service.CategoriaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoriaController.class)
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

//CORREÇÃO: O MockMvc testa apenas a camada web.
// O service precisa ser mockado porque o @WebMvcTest não carrega o contexto completo.

    @MockBean
    private CategoriaService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarCategoria() throws Exception {
        //Dado (Given)
        CategoriaRequest request = new CategoriaRequest(" Eletronicos");
        CategoriaResponse response = new CategoriaResponse(1L, "Eletronicos");

        when(service.criar(request)).thenReturn(response);

        //Quando (when) e então (Then)
        mockMvc.perform(
                post("/api/v1/categorias")
            .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            )
        .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Eletronicos"));
    }
}