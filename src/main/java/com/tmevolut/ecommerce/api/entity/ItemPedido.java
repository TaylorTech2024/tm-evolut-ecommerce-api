package com.tmevolut.ecommerce.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*; // Importa todas as anotações do Lombok de uma vez
import java.math.BigDecimal;

@Entity
@Table(name = "itens_pedido")
@Getter         // Gera automaticamente todos os métodos get...
@Setter         // Gera automaticamente todos os métodos set...
@NoArgsConstructor // Requisito do Hibernate para criar o objeto vazio
@AllArgsConstructor // Construtor completo com todos os campos
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    @JsonIgnoreProperties("itens")
    private Pedido pedido;
    // Corrigido: Aqui referenciamos o campo "itens" da classe Pedido,
    // ou simplesmente ignoramos o "pedido" para evitar loop de JSON.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    @JsonIgnoreProperties("categoria") // Exemplo: ignora a categoria do produto para não loopar
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(name = "preco_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitario;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;
}