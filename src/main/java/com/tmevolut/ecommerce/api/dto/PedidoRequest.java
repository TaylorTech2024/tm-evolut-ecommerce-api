package com.tmevolut.ecommerce.api.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
public record PedidoRequest(@NotNull Long clienteId, @NotEmpty List<@Valid ItemPedidoRequest> itens) {}
