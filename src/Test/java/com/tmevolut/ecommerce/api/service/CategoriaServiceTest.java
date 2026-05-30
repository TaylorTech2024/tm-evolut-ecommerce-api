package com.tmevolut.ecommerce.api.service;

import com.tmevolut.ecommerce.api.dto.CategoriaRequest;
import com.tmevolut.ecommerce.api.dto.CategoriaResponse;
import com.tmevolut.ecommerce.api.entity.Categoria;
import com.tmevolut.ecommerce.api.repository.CategoriaRepository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CategoriaServiceTest{

    @Test
    void deveCriarCategoria(){

        CategoriaRepository repository = Mockito.mock(CategoriaRepository.class);

        Categoria categoria = new Categoria ("Eletronicos");

        when(repository.save(Mockito.any(Categoria.class)))
                .thenReturn(categoria);

        CategoriaService service = new CategoriaService(repository);

        CategoriaRequest request =
                new CategoriaRequest("Eletronicos");

        CategoriaResponse response = service.criar (request);

        assertNotNull(response);
        assertEquals("Eletronicos",response.nome());
    }
}
