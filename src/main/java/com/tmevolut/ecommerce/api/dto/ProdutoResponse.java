package com.tmevolut.ecommerce.api.dto;

import java.math.BigDecimal;

public record ProdutoResponse(
        Long id,
        String nome,
        String sku,
        BigDecimal preco,
        Integer estoque,
        Long categoriaId,
        String categoriaNome
) {}