package com.tmevolut.ecommerce.api.controller;

import com.tmevolut.ecommerce.api.dto.PedidoRequest;
import com.tmevolut.ecommerce.api.dto.PedidoResponse;
import com.tmevolut.ecommerce.api.dto.StatusPedidoRequest;
import com.tmevolut.ecommerce.api.entity.StatusPedido;
import com.tmevolut.ecommerce.api.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Pedidos")
@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar pedidos com paginação", description = "Retorna uma lista paginada de todos os pedidos, permitindo a filtragem opcional pelo status.")
    public ResponseEntity<Page<PedidoResponse>> listar(
            @RequestParam(required = false) StatusPedido status,
            Pageable pageable) {
        return ResponseEntity.ok(service.listar(status, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID", description = "Recupera os detalhes completos e consolidados de um pedido específico com base no seu identificador único.")
    public ResponseEntity<PedidoResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    @Operation(summary = "Criar um novo pedido",
            description = "Registra a intenção de compra processando os dados do cliente e persistindo os itens do carrinho em cascata.")
    public ResponseEntity<PedidoResponse> criar(@Valid @RequestBody PedidoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @PatchMapping("/{id}/pagar")
    @Operation(summary = "Confirmar pagamento do pedido",
            description = "Transiciona o estado interno do pedido informado para o status de pago.")
    public ResponseEntity<PedidoResponse> pagar(@PathVariable Long id) {
        return ResponseEntity.ok(service.pagar(id));
    }

    @PatchMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar um pedido",
            description = "Aplica o cancelamento definitivo do pedido selecionado através do identificador único.")
    public ResponseEntity<PedidoResponse> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(service.cancelar(id));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Alterar status dinamicamente",
            description = "Permite a transição manual e direta do status do pedido (ex: ENVIADO, ENTREGUE) por operadores autorizados.")
    public ResponseEntity<PedidoResponse> alterarStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusPedidoRequest request) {

        return ResponseEntity.ok(service.alterarStatus(id, request));
    }
}