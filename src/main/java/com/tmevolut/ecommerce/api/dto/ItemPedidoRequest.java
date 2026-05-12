package com.tmevolut.ecommerce.api.dto;
import jakarta.validation.constraints.*;
public record ItemPedidoRequest(@NotNull Long produtoId, @NotNull @Min(1) Integer quantidade) {}
