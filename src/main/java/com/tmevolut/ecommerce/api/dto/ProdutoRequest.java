package com.tmevolut.ecommerce.api.dto;
import com.tmevolut.ecommerce.api.validator.SkuValido;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record ProdutoRequest(@NotBlank String nome, @SkuValido String sku, @NotNull @DecimalMin("0.01") BigDecimal preco, @Min(0) Integer estoque, @NotNull Long categoriaId) {}
