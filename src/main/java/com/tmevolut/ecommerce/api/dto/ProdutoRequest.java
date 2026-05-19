package com.tmevolut.ecommerce.api.dto;

import com.tmevolut.ecommerce.api.validator.SkuValido;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ProdutoRequest(

        @NotBlank(message = "O nome do produto é obrigatório")
        String nome,

        @SkuValido
        String sku,

        @NotNull(message = "O preço é obrigatório")
        @DecimalMin(value = "0.01", message = "O preço deve ser maior ou igual a 0.01")
        BigDecimal preco,

        @Min(value = 0, message = "O estoque não pode ser negativo")
        Integer estoque,

        @NotNull(message = "A categoria é obrigatória")
        Long categoriaId

) {}