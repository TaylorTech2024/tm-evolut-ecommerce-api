package com.tmevolut.ecommerce.api.repository;

import com.tmevolut.ecommerce.api.entity.Pedido;
import com.tmevolut.ecommerce.api.entity.StatusPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Page<Pedido> findByStatusAndDeletedAtIsNull(StatusPedido status, Pageable pageable);

    Page<Pedido> findByDeletedAtIsNull(Pageable pageable);
}