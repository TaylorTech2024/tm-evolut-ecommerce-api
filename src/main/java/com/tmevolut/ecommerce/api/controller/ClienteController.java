package com.tmevolut.ecommerce.api.controller;

import com.tmevolut.ecommerce.api.dto.ClienteRequest;
import com.tmevolut.ecommerce.api.dto.ClienteResponse;
import com.tmevolut.ecommerce.api.service.ClienteService;
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

@Tag(name = "Clientes")
@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar clientes com paginação",
            description = "Retorna uma listagem paginada de todos os clientes registrados no sistema, ideal para otimização de consultas extensas.")
    public ResponseEntity<Page<ClienteResponse>> listar(Pageable pageable) {
        Page<ClienteResponse> resultado = service.listar(pageable);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID",
            description = "Busca e retorna os dados detalhados de um cliente específico com base no seu identificador único.")
    public ResponseEntity<ClienteResponse> buscar(@PathVariable Long id) {
        ClienteResponse response = service.buscar(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Cadastrar um novo cliente",
            description = "Registra um novo cliente no sistema após validar a integridade dos dados obrigatórios fornecidos.")
    public ResponseEntity<ClienteResponse> criar(
            @Valid @RequestBody ClienteRequest request) {

        ClienteResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente existente",
            description = "Atualiza integralmente as informações cadastrais de um cliente utilizando o ID fornecido na URL.")
    public ResponseEntity<ClienteResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequest request) {

        ClienteResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover cliente por ID",
            description = "Remove definitivamente o registro de um cliente do banco de dados, retornando o status 204 No Content.")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}