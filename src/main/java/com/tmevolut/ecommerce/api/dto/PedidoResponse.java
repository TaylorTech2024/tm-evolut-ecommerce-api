package com.tmevolut.ecommerce.api.dto;
import com.tmevolut.ecommerce.api.entity.StatusPedido;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
public record PedidoResponse(Long id, Long clienteId, String clienteNome, LocalDateTime dataPedido, StatusPedido status, BigDecimal total, List<ItemPedidoResponse> itens) {}
