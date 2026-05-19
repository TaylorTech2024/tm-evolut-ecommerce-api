package com.tmevolut.ecommerce.api.repository;

import com.tmevolut.ecommerce.api.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Page<Produto> findByDeletedAtIsNull(Pageable pageable);

    Page<Produto> findByNomeContainingIgnoreCaseAndDeletedAtIsNull(String nome, Pageable pageable);
}