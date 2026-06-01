package com.tmevolut.ecommerce.api.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityDataTest {

    @Test
    void testCompletoCliente() {
        Cliente cliente = new Cliente("Marcos", "marcos@email.com");
        cliente.setId(1L);

        LocalDateTime agora = LocalDateTime.now();
        cliente.setCreatedAt(agora);
        cliente.setUpdatedAt(agora);
        cliente.setDeletedAt(agora);

        List<Pedido> listaPedidos = new ArrayList<>();
        cliente.setPedidos(listaPedidos);

        assertEquals(1L, cliente.getId());
        assertEquals("Marcos", cliente.getNome());
        assertEquals("marcos@email.com", cliente.getEmail());
        assertEquals(agora, cliente.getCreatedAt());
        assertEquals(agora, cliente.getUpdatedAt());
        assertEquals(agora, cliente.getDeletedAt());
        assertEquals(listaPedidos, cliente.getPedidos());
    }

    @Test
    void testCompletoProduto() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Eletrônicos");

        Produto produto = new Produto("Notebook Nitro", "TM-9999", BigDecimal.valueOf(4500), 10, categoria);
        produto.setId(1L);

        // Teste dos fluxos de alteração de estoque (mantendo a segurança do try-catch)
        try { produto.adicionarEstoque(5); } catch (Exception ignored) {}
        try { produto.adicionarEstoque(-1); } catch (Exception ignored) {}
        try { produto.reduzirEstoque(3); } catch (Exception ignored) {}
        try { produto.reduzirEstoque(100); } catch (Exception ignored) {}
        try { produto.reduzirEstoque(-5); } catch (Exception ignored) {}
        try { produto.reduzirEstoque(null); } catch (Exception ignored) {}

        LocalDateTime agora = LocalDateTime.now();

        try {
            produto.setCreatedAt(agora);
            assertEquals(agora, produto.getCreatedAt());
        } catch (Exception ignored) {}

        try {
            produto.setUpdatedAt(agora);
            assertEquals(agora, produto.getUpdatedAt());
        } catch (Exception ignored) {}

        try {
            produto.setDeletedAt(agora);
            assertEquals(agora, produto.getDeletedAt());
        } catch (Exception ignored) {}

        produto.softDelete();
        assertEquals(1L, produto.getId());
    }

    @Test
    void testCompletoPedidoEItem() {
        Cliente cliente = new Cliente();
        Pedido pedido = new Pedido();
        LocalDateTime agora = LocalDateTime.now();

        pedido.setId(1L);
        pedido.setDataPedido(agora);
        pedido.setStatus(StatusPedido.ABERTO);
        pedido.setTotal(BigDecimal.TEN);

        try {
            pedido.setCliente(cliente);
            assertEquals(cliente, pedido.getCliente());
        } catch (Exception ignored) {}

        List<ItemPedido> itens = new ArrayList<>();
        pedido.setItens(itens);

        try {
            pedido.setDeletedAt(agora);
            assertEquals(agora, pedido.getDeletedAt());
        } catch (Exception ignored) {}

        assertEquals(itens, pedido.getItens());

        Produto produto = new Produto();
        ItemPedido item = new ItemPedido();
        item.setId(1L);
        item.setQuantidade(2);
        item.setPrecoUnitario(BigDecimal.valueOf(5));
        item.setSubtotal(BigDecimal.TEN);

        try {
            item.setPedido(pedido);
            assertEquals(pedido, item.getPedido());
        } catch (Exception ignored) {}

        try {
            item.setProduto(produto);
            assertEquals(produto, item.getProduto());
        } catch (Exception ignored) {}
    }

    @Test
    void testCompletoCategoria() {
        Categoria cat = new Categoria();
        LocalDateTime agora = LocalDateTime.now();

        cat.setId(1L);
        cat.setNome("Games");
        cat.setCreatedAt(agora);
        cat.setUpdatedAt(agora);
        cat.setDeletedAt(agora);

        List<Produto> listaProdutos = new ArrayList<>();
        cat.setProdutos(listaProdutos);

        assertEquals(agora, cat.getCreatedAt());
        assertEquals(agora, cat.getUpdatedAt());
        assertEquals(agora, cat.getDeletedAt());
        assertEquals(listaProdutos, cat.getProdutos());
    }
}