package com.tmevolut.ecommerce.api.controller;

import com.tmevolut.ecommerce.api.dto.CategoriaRequest;
import com.tmevolut.ecommerce.api.dto.CategoriaResponse;
import com.tmevolut.ecommerce.api.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Listar categorias com paginação",
            description = "Retorna uma listagem paginada de todas as categorias cadastradas para otimização do consumo de dados pelo front-end.")
    public ResponseEntity<Page<CategoriaResponse>> listar(Pageable pageable) {
        Page<CategoriaResponse> resultant = service.listar(pageable);
        return ResponseEntity.ok(resultant);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoria por ID",
            description = "Busca e recupera os detalhes de uma categoria específica no banco de dados utilizando seu identificador único.")
    public ResponseEntity<CategoriaResponse> buscar(@PathVariable Long id) {
        CategoriaResponse response = service.buscar(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Cadastrar uma nova categoria",
            description = "Registra uma nova categoria de produtos no sistema após a validação dos dados de entrada.")
    public ResponseEntity<CategoriaResponse> criar(
            @Valid @RequestBody CategoriaRequest request) {

        CategoriaResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar categoria existente",
            description = "Substitui integralmente os dados de uma categoria existente identificada pelo ID fornecido na URL.")
    public ResponseEntity<CategoriaResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequest request) {

        CategoriaResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover categoria por ID",
            description = "Exclui definitivamente uma categoria do banco de dados através do seu identificador único, retornando o status 204 No Content.")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}