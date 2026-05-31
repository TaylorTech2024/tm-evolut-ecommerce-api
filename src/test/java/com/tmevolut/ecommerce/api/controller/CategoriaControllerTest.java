package com.tmevolut.ecommerce.api.controller;

import com.tmevolut.ecommerce.api.service.CategoriaService;
import com.tmevolut.ecommerce.api.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoriaController.class)
@Import(GlobalExceptionHandler.class)
public class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoriaService categoriaService;

    @Test
    void testTodosEndpointsCategoria() throws Exception {
        // 1. Teste do GET (listar).
        mockMvc.perform(get("/api/v1/categorias")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // 2. Teste do GET (buscar por ID).
        mockMvc.perform(get("/api/v1/categorias/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // JSON estruturado para criação e atualização.
        String jsonCategoria = """
                {   "nome": "Eletrônicos"  }
                """;

        // 3. Teste do POST (criar) - Mantendo o que já estava cobrindo.
        mockMvc.perform(post("/api/v1/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCategoria))
                .andExpect(status().isCreated());

        // 4. Teste do PUT (atualizar).
        mockMvc.perform(put("/api/v1/categorias/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCategoria))
                .andExpect(status().isOk());

        // 5. Teste do DELETE (remover).
        mockMvc.perform(delete("/api/v1/categorias/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}