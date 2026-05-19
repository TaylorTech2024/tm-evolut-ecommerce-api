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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

        Page<PedidoResponse> resultado = service.listar(status, pageable);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscar(@PathVariable Long id) {
        PedidoResponse response = service.buscar(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> criar(
            @Valid @RequestBody PedidoRequest request) {

        PedidoResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}/pagar")
    public ResponseEntity<PedidoResponse> pagar(@PathVariable Long id) {
        PedidoResponse response = service.pagar(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<PedidoResponse> cancelar(@PathVariable Long id) {
        PedidoResponse response = service.cancelar(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponse> alterarStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusPedidoRequest request) {

        PedidoResponse response = service.alterarStatus(id, request);
        return ResponseEntity.ok(response);
    }
}