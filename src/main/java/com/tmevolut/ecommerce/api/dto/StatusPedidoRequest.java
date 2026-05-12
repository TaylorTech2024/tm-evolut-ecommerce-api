package com.tmevolut.ecommerce.api.dto;
import com.tmevolut.ecommerce.api.entity.StatusPedido;
import jakarta.validation.constraints.NotNull;
public record StatusPedidoRequest(@NotNull StatusPedido status) {}
