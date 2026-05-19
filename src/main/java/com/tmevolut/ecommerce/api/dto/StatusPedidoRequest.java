package com.tmevolut.ecommerce.api.dto;

import com.tmevolut.ecommerce.api.entity.StatusPedido;
import jakarta.validation.constraints.NotNull;

public record StatusPedidoRequest(

        @NotNull(message = "O status do pedido é obrigatório")
        StatusPedido status

) {}