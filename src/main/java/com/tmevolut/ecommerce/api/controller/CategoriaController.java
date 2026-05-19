package com.tmevolut.ecommerce.api.controller;

import com.tmevolut.ecommerce.api.dto.CategoriaRequest;
import com.tmevolut.ecommerce.api.dto.CategoriaResponse;
import com.tmevolut.ecommerce.api.service.CategoriaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Categorias")
@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<CategoriaResponse>> listar(Pageable pageable) {
        Page<CategoriaResponse> resultado = service.listar(pageable);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> buscar(@PathVariable Long id) {
        CategoriaResponse response = service.buscar(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> criar(
            @Valid @RequestBody CategoriaRequest request) {

        CategoriaResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequest request) {

        CategoriaResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}