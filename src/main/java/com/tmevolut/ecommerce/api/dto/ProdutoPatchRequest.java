package com.tmevolut.ecommerce.api.dto;

import java.math.BigDecimal;

public record ProdutoPatchRequest(
        String nome,
        String sku,
        BigDecimal preco,
        Integer estoque,
        Long categoriaId
) {}