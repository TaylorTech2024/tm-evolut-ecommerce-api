package com.tmevolut.ecommerce.api.repository;
import com.tmevolut.ecommerce.api.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tmevolut.ecommerce.api.entity.StatusPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Page<Pedido> findByStatus(StatusPedido status, Pageable pageable);
}
