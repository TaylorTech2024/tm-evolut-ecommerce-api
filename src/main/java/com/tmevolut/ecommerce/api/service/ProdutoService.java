package com.tmevolut.ecommerce.api.service;

import com.tmevolut.ecommerce.api.dto.ProdutoPatchRequest;
import com.tmevolut.ecommerce.api.dto.ProdutoRequest;
import com.tmevolut.ecommerce.api.dto.ProdutoResponse;
import com.tmevolut.ecommerce.api.entity.*;
import com.tmevolut.ecommerce.api.exception.ResourceNotFoundException;
import com.tmevolut.ecommerce.api.repository.ProdutoRepository;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;
    private final CategoriaService categoriaService;

    public ProdutoService(ProdutoRepository repository, CategoriaService categoriaService) {
        this.repository = repository;
        this.categoriaService = categoriaService;
    }

    @Transactional
    @CacheEvict(value = "produto-individual", key = "#result.id")
    public ProdutoResponse criar(ProdutoRequest request) {
        Categoria categoria = categoriaService.buscarEntidade(request.categoriaId());
        Produto p = new Produto(request.nome(), request.sku(), request.preco(), request.estoque(), categoria);
        return toResponse(repository.save(p));
    }

    @Transactional(readOnly = true)
    public Page<ProdutoResponse> listar(String nome, Pageable pageable) {
        Page<Produto> page = (nome == null || nome.isBlank())
                ? repository.findByDeletedAtIsNull(pageable)
                : repository.findByNomeContainingIgnoreCaseAndDeletedAtIsNull(nome, pageable);
        return page.map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public Produto buscarEntidade(Long id) {
        return repository.findById(id)
                .filter(p -> p.getDeletedAt() == null)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "produto-individual", key = "#id")
    public ProdutoResponse buscar(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional
    @CacheEvict(value = "produto-individual", key = "#id")
    public ProdutoResponse atualizar(Long id, ProdutoRequest request) {
        Produto p = buscarEntidade(id);
        Categoria categoria = categoriaService.buscarEntidade(request.categoriaId());

        p.setNome(request.nome());
        p.setSku(request.sku());
        p.setPreco(request.preco());
        p.setEstoque(request.estoque());
        p.setCategoria(categoria);

        return toResponse(repository.save(p));
    }

    @Transactional
    @CacheEvict(value = "produto-individual", key = "#id")
    public ProdutoResponse actualizerParcial(Long id, ProdutoPatchRequest request) {
        Produto p = buscarEntidade(id);

        if (request.nome() != null) p.setNome(request.nome());
        if (request.sku() != null) p.setSku(request.sku());
        if (request.preco() != null) p.setPreco(request.preco());
        if (request.estoque() != null) p.setEstoque(request.estoque());
        if (request.categoriaId() != null) p.setCategoria(categoriaService.buscarEntidade(request.categoriaId()));

        return toResponse(repository.save(p));
    }

    @Transactional
    @CacheEvict(value = "produto-individual", key = "#id")
    public void remover(Long id) {
        Produto p = buscarEntidade(id);
        p.setDeletedAt(LocalDateTime.now());
        repository.save(p);
    }

    private ProdutoResponse toResponse(Produto p) {
        return new ProdutoResponse(
                p.getId(),
                p.getNome(),
                p.getSku(),
                p.getPreco(),
                p.getEstoque(),
                p.getCategoria().getId(),
                p.getCategoria().getNome()
        );
    }
}