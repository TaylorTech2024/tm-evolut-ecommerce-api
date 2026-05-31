package com.tmevolut.ecommerce.api.controller;

import com.tmevolut.ecommerce.api.dto.ClienteResponse;
import com.tmevolut.ecommerce.api.service.ClienteService;
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

@WebMvcTest(ClienteController.class)
@Import(GlobalExceptionHandler.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClienteService clienteService;

    @Test
    void testTodosEndpointsCliente() throws Exception {
        // 1. Teste do GET (listar) - O que já estava funcionando.
        mockMvc.perform(get("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // 2. Teste do GET (buscar por ID).
        mockMvc.perform(get("/api/v1/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // JSON base para criação e atualização de cliente
        String jsonCliente = """
            {
                "nome": "Marcos Silva",
                "email": "marcos@email.com"
            }
            """;

        // 3. Teste do POST (criar).
        mockMvc.perform(post("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCliente))
                .andExpect(status().isCreated());

        // 4. Teste do PUT (atualizar).
        mockMvc.perform(put("/api/v1/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCliente))
                .andExpect(status().isOk());

        // 5. Teste do DELETE (remover).
        mockMvc.perform(delete("/api/v1/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}