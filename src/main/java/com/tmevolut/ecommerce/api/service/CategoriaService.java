package com.tmevolut.ecommerce.api.service;

import com.tmevolut.ecommerce.api.dto.*;
import com.tmevolut.ecommerce.api.entity.Categoria;
import com.tmevolut.ecommerce.api.exception.ResourceNotFoundException;
import com.tmevolut.ecommerce.api.repository.CategoriaRepository;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class CategoriaService {
    private final CategoriaRepository repository;
    public CategoriaService(CategoriaRepository repository) { this.repository = repository; }

    @Transactional @CacheEvict(value = "categorias", allEntries = true)
    public CategoriaResponse criar(CategoriaRequest request) { return toResponse(repository.save(new Categoria(request.nome()))); }

    @Cacheable("categorias")
    public Page<CategoriaResponse> listar(Pageable pageable) { return repository.findByDeletedAtIsNull(pageable).map(this::toResponse); }

    public Categoria buscarEntidade(Long id) { return repository.findById(id).filter(c -> c.getDeletedAt() == null).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada")); }

    public CategoriaResponse buscar(Long id) { return toResponse(buscarEntidade(id)); }

    @Transactional @CacheEvict(value = "categorias", allEntries = true)
    public CategoriaResponse atualizar(Long id, CategoriaRequest request) { Categoria c = buscarEntidade(id); c.setNome(request.nome()); return toResponse(repository.save(c)); }

    @Transactional @CacheEvict(value = "categorias", allEntries = true)
    public void remover(Long id) { Categoria c = buscarEntidade(id); c.setDeletedAt(LocalDateTime.now()); repository.save(c); }

    private CategoriaResponse toResponse(Categoria c) { return new CategoriaResponse(c.getId(), c.getNome()); }
}
