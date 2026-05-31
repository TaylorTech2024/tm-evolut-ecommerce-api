package com.tmevolut.ecommerce.api.repository;

import com.tmevolut.ecommerce.api.entity.Categoria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CategoriaRepositoryTest {

    @Autowired
    private CategoriaRepository repository;

    @Test
    void deveSalvarCategoria() {

        Categoria categoria = new Categoria("Eletrônicos");

        Categoria salva = repository.save(categoria);

        assertThat(salva.getId()).isNotNull();
        assertThat(salva.getNome()).isEqualTo("Eletrônicos");
    }

    @Test
    void deveListarCategoriasNaoDeletadas() {

        Categoria categoria = new Categoria("Informática");

        repository.save(categoria);

        Page<Categoria> resultado =
                repository.findByDeletedAtIsNull(PageRequest.of(0, 10));

        assertThat(resultado.getContent()).isNotEmpty();
    }
}