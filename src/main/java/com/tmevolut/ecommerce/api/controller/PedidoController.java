package com.tmevolut.ecommerce.api.controller;

import com.tmevolut.ecommerce.api.dto.PedidoRequest;
import com.tmevolut.ecommerce.api.dto.PedidoResponse;
import com.tmevolut.ecommerce.api.dto.StatusPedidoRequest;
import com.tmevolut.ecommerce.api.entity.StatusPedido;
import com.tmevolut.ecommerce.api.service.PedidoService;
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
    public ResponseEntity<Page<PedidoResponse>> listar(
            @RequestParam(required = false) StatusPedido status,
            Pageable pageable) {
        return ResponseEntity.ok(service.listar(status, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> criar(@Valid @RequestBody PedidoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @PatchMapping("/{id}/pagar")
    public ResponseEntity<PedidoResponse> pagar(@PathVariable Long id) {
        return ResponseEntity.ok(service.pagar(id));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<PedidoResponse> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(service.cancelar(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponse> alterarStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusPedidoRequest request) {

          return ResponseEntity.ok(service.alterarStatus(id, request));
    }
}