package com.tmevolut.ecommerce.api.repository;

import com.tmevolut.ecommerce.api.entity.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Page<Categoria> findByDeletedAtIsNull(Pageable pageable);
}