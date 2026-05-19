package com.tmevolut.ecommerce.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record PedidoRequest(

        @NotNull(message = "O ID do cliente é obrigatório")
        Long clienteId,

        @NotEmpty(message = "O pedido deve conter pelo menos um item")
        List<@Valid ItemPedidoRequest> itens

) {}