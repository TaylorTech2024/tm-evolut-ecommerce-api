package com.tmevolut.ecommerce.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*; // Importa todas as anotações do Lombok
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter          // Gera todos os getters automaticamente
@Setter          // Gera todos os setters automaticamente
@NoArgsConstructor // Gera o construtor vazio obrigatório pelo JPA
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_pedido", nullable = false)
    private LocalDateTime dataPedido = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusPedido status = StatusPedido.ABERTO;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("pedido")
    private List<ItemPedido> itens = new ArrayList<>();

    // O método de negócio PRECISA ser mantido.
    // Ele é fundamental para a integridade do relacionamento bidirecional.
    public void addItem(ItemPedido item) {
        item.setPedido(this);
        this.itens.add(item);
    }
}