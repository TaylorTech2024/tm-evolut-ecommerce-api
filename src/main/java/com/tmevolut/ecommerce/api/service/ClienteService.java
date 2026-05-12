package com.tmevolut.ecommerce.api.service;

import com.tmevolut.ecommerce.api.dto.*;
import com.tmevolut.ecommerce.api.entity.Cliente;
import com.tmevolut.ecommerce.api.exception.ResourceNotFoundException;
import com.tmevolut.ecommerce.api.repository.ClienteRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class ClienteService {
    private final ClienteRepository repository;
    public ClienteService(ClienteRepository repository) { this.repository = repository; }
    @Transactional public ClienteResponse criar(ClienteRequest request) { return toResponse(repository.save(new Cliente(request.nome(), request.email()))); }
    public Page<ClienteResponse> listar(Pageable pageable) { return repository.findByDeletedAtIsNull(pageable).map(this::toResponse); }
    public Cliente buscarEntidade(Long id) { return repository.findById(id).filter(c -> c.getDeletedAt() == null).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado")); }
    public ClienteResponse buscar(Long id) { return toResponse(buscarEntidade(id)); }
    @Transactional public ClienteResponse atualizar(Long id, ClienteRequest request) { Cliente c = buscarEntidade(id); c.setNome(request.nome()); c.setEmail(request.email()); return toResponse(repository.save(c)); }
    @Transactional public void remover(Long id) { Cliente c = buscarEntidade(id); c.setDeletedAt(LocalDateTime.now()); repository.save(c); }
    private ClienteResponse toResponse(Cliente c) { return new ClienteResponse(c.getId(), c.getNome(), c.getEmail()); }
}
