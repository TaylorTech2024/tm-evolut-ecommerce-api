package com.tmevolut.ecommerce.api.repository;

import com.tmevolut.ecommerce.api.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Page<Cliente> findByDeletedAtIsNull(Pageable pageable);
}