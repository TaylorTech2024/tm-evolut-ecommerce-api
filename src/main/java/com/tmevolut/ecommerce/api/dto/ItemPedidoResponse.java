package com.tmevolut.ecommerce.api.dto;

import java.math.BigDecimal;

public record ItemPedidoResponse(
        Long produtoId,
        String produtoNome,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal
) {}