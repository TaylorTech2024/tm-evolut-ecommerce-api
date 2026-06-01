package com.tmevolut.ecommerce.api.controller;

import com.tmevolut.ecommerce.api.dto.ProdutoPatchRequest;
import com.tmevolut.ecommerce.api.dto.ProdutoRequest;
import com.tmevolut.ecommerce.api.dto.ProdutoResponse;
import com.tmevolut.ecommerce.api.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Produtos")
@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar produtos com paginação",
            description = "Retorna uma listagem paginada de todos os produtos do catálogo, permitindo filtragem opcional por nome.")
    public ResponseEntity<Page<ProdutoResponse>> listar(
            @RequestParam(required = false) String nome,
            Pageable pageable) {
        return ResponseEntity.ok(service.listar(nome, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID",
            description = "Recupera as informações detalhadas de um produto específico através do seu identificador único.")
    public ResponseEntity<ProdutoResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    @Operation(summary = "Cadastrar um novo produto",
            description = "Registra um novo produto no catálogo do e-commerce após a validação das regras de negócio e preço.")
    public ResponseEntity<ProdutoResponse> criar(@Valid @RequestBody ProdutoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto existente (Total)",
            description = "Substitui integralmente todos os dados de um produto cadastrado utilizando o ID fornecido.")
    public ResponseEntity<ProdutoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar produto parcialmente (Patch)",
            description = "Modifica apenas os campos específicos enviados no corpo da requisição (ex: apenas o preço ou apenas o estoque) sem alterar o restante do registro.")
    public ResponseEntity<ProdutoResponse> atualizarParcial(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoPatchRequest request) {

        return ResponseEntity.ok(service.atualizarParcial(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover produto por ID",
            description = "Exclui permanentemente um produto do catálogo do sistema, retornando o status 204 No Content.")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}