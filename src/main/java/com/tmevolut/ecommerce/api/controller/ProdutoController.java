package com.tmevolut.ecommerce.api.controller;

import com.tmevolut.ecommerce.api.dto.ProdutoPatchRequest;
import com.tmevolut.ecommerce.api.dto.ProdutoRequest;
import com.tmevolut.ecommerce.api.dto.ProdutoResponse;
import com.tmevolut.ecommerce.api.service.ProdutoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Produtos")
@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoResponse>> listar(
            @RequestParam(required = false) String nome,
            Pageable pageable) {

        Page<ProdutoResponse> resultado = service.listar(nome, pageable);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscar(@PathVariable Long id) {
        ProdutoResponse response = service.buscar(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> criar(
            @Valid @RequestBody ProdutoRequest request) {

        ProdutoResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoRequest request) {

        ProdutoResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizarParcial(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoPatchRequest request) {

        ProdutoResponse response = service.actualizerParcial(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}