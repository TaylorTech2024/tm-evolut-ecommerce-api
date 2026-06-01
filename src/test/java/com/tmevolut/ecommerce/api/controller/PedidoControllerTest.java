package com.tmevolut.ecommerce.api.controller;

import com.tmevolut.ecommerce.api.service.PedidoService;
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

@WebMvcTest(PedidoController.class)
@Import(GlobalExceptionHandler.class)
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PedidoService pedidoService;

    @Test
    void testTodosEndpointsPedido() throws Exception {
        String jsonPedidoValido = """
            {
                "clienteId": 1,
                "itens": [
                    {
                        "produtoId": 1,
                        "quantidade": 2
                    }
                ]
            }
            """;

        mockMvc.perform(post("/api/v1/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPedidoValido))
                .andExpect(status().isCreated());

        // 2. Teste do GET (listar).
        mockMvc.perform(get("/api/v1/pedidos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // 3. Teste do GET (buscar por ID).
        mockMvc.perform(get("/api/v1/pedidos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // 4. Teste do PATCH (alterarStatus).
        mockMvc.perform(patch("/api/v1/pedidos/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\": \"PAGO\"}"))
                .andExpect(status().isOk());

        // 5. Teste do PATCH (pagar).
        mockMvc.perform(patch("/api/v1/pedidos/1/pagar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // 6. Teste do PATCH/DELETE (cancelar).
        mockMvc.perform(patch("/api/v1/pedidos/1/cancelar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}