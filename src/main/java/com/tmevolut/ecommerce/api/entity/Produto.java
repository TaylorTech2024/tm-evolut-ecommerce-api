package com.tmevolut.ecommerce.api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "produtos")
@Getter                  // Lombok gera todos os Getters automaticamente
@Setter                  // Lombok gera todos os Setters automaticamente
@NoArgsConstructor       // Lombok gera o construtor vazio sozinho! (Remove a necessidade do manual)
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, unique = true, length = 50)
    private String sku;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(nullable = false)
    private Integer estoque;

    private LocalDateTime deletedAt;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    // Este construtor customizado para criar novos produtos no Service de forma fácil
    public Produto(String nome, String sku, BigDecimal preco, Integer estoque, Categoria categoria) {
        this.nome = nome;
        this.sku = sku;
        this.preco = preco;
        this.estoque = estoque;
        this.categoria = categoria;
    }

    // --- Lógica de Negócio (Encapsulada na Entidade) ---

    public void reduzirEstoque(Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        if (this.estoque < quantidade) {
            throw new IllegalStateException("Estoque insuficiente para o produto: " + this.nome);
        }
        this.estoque -= quantidade;
    }

    public void adicionarEstoque(Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        this.estoque += quantidade;
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }
}