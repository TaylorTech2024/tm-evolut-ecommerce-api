package com.tmevolut.ecommerce.api.controller;

import com.tmevolut.ecommerce.api.dto.ProdutoResponse;
import com.tmevolut.ecommerce.api.service.ProdutoService;
import com.tmevolut.ecommerce.api.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProdutoController.class)
@Import(GlobalExceptionHandler.class)
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProdutoService produtoService;

    @Test
    void testTodosEndpointsProduto() throws Exception {
        ProdutoResponse responseMock = new ProdutoResponse(
                1L, "Teclado", "TM-1111", BigDecimal.valueOf(150), 10, 1L, "Periféricos"
        );
        when(produtoService.buscar(1L)).thenReturn(responseMock);

        // 1. GET (listar)
        mockMvc.perform(get("/api/v1/produtos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // 2. GET (buscar).
        mockMvc.perform(get("/api/v1/produtos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Payload válido contendo os dados do produto conforme o padrão de SKU exigido (LETRAS-NÚMEROS).
        String jsonProduto = """
            {
                "nome": "Mouse Gamer",
                "sku": "TM-0002",
                "preco": 120.50,
                "estoque": 50,
                "categoriaId": 1
            }
            """;

        // 3. POST (criar) - Agora vai retornar 201!
        mockMvc.perform(post("/api/v1/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProduto))
                .andExpect(status().isCreated());

        // 4. PUT (atualizar completo).
        mockMvc.perform(put("/api/v1/produtos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProduto))
                .andExpect(status().isOk());

        // 5. PATCH (atualizar parcial).
        String jsonPatch = "{\"preco\": 110.00}";
        mockMvc.perform(patch("/api/v1/produtos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPatch))
                .andExpect(status().isOk());

        // 6. DELETE (remover).
        mockMvc.perform(delete("/api/v1/produtos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}