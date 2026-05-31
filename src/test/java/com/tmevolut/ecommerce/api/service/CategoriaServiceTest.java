package com.tmevolut.ecommerce.api.service;

import com.tmevolut.ecommerce.api.dto.CategoriaRequest;
import com.tmevolut.ecommerce.api.dto.CategoriaResponse;
import com.tmevolut.ecommerce.api.entity.Categoria;
import com.tmevolut.ecommerce.api.repository.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository repository;

    @InjectMocks
    private CategoriaService service;

    @Test
    void deveCriarCategoria() {
        Categoria categoria = new Categoria("Eletrônicos");
        when(repository.save(any(Categoria.class))).thenReturn(categoria);

        CategoriaRequest request = new CategoriaRequest("Eletrônicos");
        CategoriaResponse response = service.criar(request);

        assertNotNull(response);
        assertEquals("Eletrônicos", response.nome());
    }

    @Test
    void deveBuscarCategoriaPorIdComSucesso() {
        Categoria categoria = new Categoria("Eletrônicos");
        categoria.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(categoria));

        CategoriaResponse response = service.buscar(1L);

        assertNotNull(response);
        assertEquals("Eletrônicos", response.nome());
    }

    @Test
    void deveLancarExcecaoQuandoBuscarIdInexistente() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.buscar(1L));
    }

    @Test
    void deveListarCategoriasComPaginacao() {
        Categoria categoria = new Categoria("Eletrônicos");
        Pageable pageable = PageRequest.of(0, 10);
        Page<Categoria> paginaMock = new PageImpl<>(List.of(categoria));

        when(repository.findByDeletedAtIsNull(pageable)).thenReturn(paginaMock);

        Page<CategoriaResponse> resultado = service.listar(pageable);

        assertNotNull(resultado);
        assertEquals(1, resultado.getContent().size());
        assertEquals("Eletrônicos", resultado.getContent().get(0).nome());
    }

    @Test
    void deveAtualizarCategoriaComSucesso() {
        Categoria categoriaAntiga = new Categoria("Eletrônicos");
        when(repository.findById(1L)).thenReturn(Optional.of(categoriaAntiga));
        when(repository.save(any(Categoria.class))).thenReturn(categoriaAntiga);

        CategoriaRequest requestAlterar = new CategoriaRequest("Livros");
        CategoriaResponse response = service.atualizar(1L, requestAlterar);

        assertNotNull(response);
        Mockito.verify(repository, Mockito.times(1)).save(any(Categoria.class));
    }

    @Test
    void deveRemoverCategoriaComSucesso() {
        Categoria categoria = new Categoria("Eletrônicos");
        when(repository.findById(1L)).thenReturn(Optional.of(categoria));

        when(repository.save(any(Categoria.class))).thenReturn(categoria);

        assertDoesNotThrow(() -> service.remover(1L));

        Mockito.verify(repository, Mockito.times(1)).save(any(Categoria.class));
    }
}