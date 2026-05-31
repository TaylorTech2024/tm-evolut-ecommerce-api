package com.tmevolut.ecommerce.api.service;

import com.tmevolut.ecommerce.api.dto.ProdutoPatchRequest;
import com.tmevolut.ecommerce.api.dto.ProdutoRequest;
import com.tmevolut.ecommerce.api.dto.ProdutoResponse;
import com.tmevolut.ecommerce.api.entity.Categoria;
import com.tmevolut.ecommerce.api.entity.Produto;
import com.tmevolut.ecommerce.api.exception.ResourceNotFoundException;
import com.tmevolut.ecommerce.api.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository repository;

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private ProdutoService service;

    private Categoria categoria;
    private Produto produto;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0, 10);
        categoria = new Categoria("Eletrônicos");
        categoria.setId(1L);

        produto = new Produto("Notebook", "SKU123", BigDecimal.valueOf(4500.0), 10, categoria);
        produto.setId(1L);
    }

    @Test
    void deveCriarProdutoComSucesso() {
        ProdutoRequest request = new ProdutoRequest("Notebook", "SKU123", BigDecimal.valueOf(4500.0), 10, 1L);

        when(categoriaService.buscarEntidade(1L)).thenReturn(categoria);
        when(repository.save(any(Produto.class))).thenReturn(produto);

        ProdutoResponse response = service.criar(request);

        assertNotNull(response);
        assertEquals("Notebook", response.nome());
    }

    @Test
    void deveListarProdutosSemFiltroDeNomeQuandoNomeForNuloOuVazio() {
        Page<Produto> page = new PageImpl<>(List.of(produto));
        when(repository.findByDeletedAtIsNull(pageable)).thenReturn(page);

        Page<ProdutoResponse> resultadoNull = service.listar(null, pageable);
        Page<ProdutoResponse> resultadoBlank = service.listar("   ", pageable);

        assertNotNull(resultadoNull);
        assertNotNull(resultadoBlank);
        verify(repository, times(2)).findByDeletedAtIsNull(pageable);
    }

    @Test
    void deveListarProdutosFiltrandoPorNome() {
        Page<Produto> page = new PageImpl<>(List.of(produto));
        when(repository.findByNomeContainingIgnoreCaseAndDeletedAtIsNull(eq("Notebook"), any(Pageable.class))).thenReturn(page);

        Page<ProdutoResponse> resultado = service.listar("Notebook", pageable);

        assertNotNull(resultado);
        verify(repository, times(1)).findByNomeContainingIgnoreCaseAndDeletedAtIsNull(eq("Notebook"), any(Pageable.class));
    }

    @Test
    void deveBuscarProdutoPorIdComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(produto));

        ProdutoResponse response = service.buscar(1L);

        assertNotNull(response);
        assertEquals("Notebook", response.nome());
    }

    @Test
    void deveLancarExcecaoQuandoProdutoEstiverDeletadoLogicamente() {
        produto.setDeletedAt(LocalDateTime.now());
        when(repository.findById(1L)).thenReturn(Optional.of(produto));

        assertThrows(ResourceNotFoundException.class, () -> service.buscar(1L));
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoExistirNoBanco() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.buscar(1L));
    }

    @Test
    void deveAtualizarProdutoComSucesso() {
        ProdutoRequest request = new ProdutoRequest("Notebook Alterado", "SKU123", BigDecimal.valueOf(4800.0), 5, 1L);

        when(repository.findById(1L)).thenReturn(Optional.of(produto));
        when(categoriaService.buscarEntidade(1L)).thenReturn(categoria);
        when(repository.save(any(Produto.class))).thenReturn(produto);

        ProdutoResponse response = service.atualizar(1L, request);

        assertNotNull(response);
        verify(repository, times(1)).save(any(Produto.class));
    }

    @Test
    void deveAtualizarParcialmenteQuandoTodosOsCamposPreenchidos() {
        ProdutoPatchRequest requestCompleto = new ProdutoPatchRequest("Nome Novo", "SKU-NEW", BigDecimal.valueOf(5000.0), 20, 1L);

        when(repository.findById(1L)).thenReturn(Optional.of(produto));
        when(categoriaService.buscarEntidade(1L)).thenReturn(categoria);
        when(repository.save(any(Produto.class))).thenReturn(produto);

        ProdutoResponse response = service.atualizarParcial(1L, requestCompleto);

        assertNotNull(response);
        verify(repository, times(1)).save(any(Produto.class));
    }

    @Test
    void deveAtualizarParcialmenteQuandoNenhumCampoPreenchido() {
        ProdutoPatchRequest requestVazio = new ProdutoPatchRequest(null, null, null, null, null);

        when(repository.findById(1L)).thenReturn(Optional.of(produto));
        when(repository.save(any(Produto.class))).thenReturn(produto);

        ProdutoResponse response = service.atualizarParcial(1L, requestVazio);

        assertNotNull(response);
        verify(categoriaService, never()).buscarEntidade(anyLong());
    }

    @Test
    void deveRemoverProdutoComSoftDelete() {
        when(repository.findById(1L)).thenReturn(Optional.of(produto));
        when(repository.save(any(Produto.class))).thenReturn(produto);

        assertDoesNotThrow(() -> service.remover(1L));
        verify(repository, times(1)).save(any(Produto.class));
    }
}