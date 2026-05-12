package com.tmevolut.ecommerce.api.service;

import com.tmevolut.ecommerce.api.dto.*;
import com.tmevolut.ecommerce.api.entity.*;
import com.tmevolut.ecommerce.api.exception.*;
import com.tmevolut.ecommerce.api.repository.PedidoRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository repository;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;
    public PedidoService(PedidoRepository repository, ClienteService clienteService, ProdutoService produtoService) { this.repository = repository; this.clienteService = clienteService; this.produtoService = produtoService; }
    @Transactional
    public PedidoResponse criar(PedidoRequest request) {
        Pedido pedido = new Pedido(); pedido.setCliente(clienteService.buscarEntidade(request.clienteId())); pedido.setStatus(StatusPedido.ABERTO);
        BigDecimal total = BigDecimal.ZERO;
        for (ItemPedidoRequest itemRequest : request.itens()) {
            Produto produto = produtoService.buscarEntidade(itemRequest.produtoId());
            if (produto.getEstoque() < itemRequest.quantidade()) throw new BusinessException("Estoque insuficiente para o produto: " + produto.getNome());
            produto.setEstoque(produto.getEstoque() - itemRequest.quantidade());
            ItemPedido item = new ItemPedido(); item.setProduto(produto); item.setQuantidade(itemRequest.quantidade()); item.setPrecoUnitario(produto.getPreco());
            BigDecimal subtotal = produto.getPreco().multiply(BigDecimal.valueOf(itemRequest.quantidade())); item.setSubtotal(subtotal);
            pedido.addItem(item); total = total.add(subtotal);
        }
        pedido.setTotal(total); return toResponse(repository.save(pedido));
    }
    public Page<PedidoResponse> listar(StatusPedido status, Pageable pageable) {
        Page<Pedido> page = status == null ? repository.findAll(pageable) : repository.findByStatus(status, pageable);
        return page.map(this::toResponse);
    }
    public Pedido buscarEntidade(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado")); }
    public PedidoResponse buscar(Long id) { return toResponse(buscarEntidade(id)); }
    @Transactional public PedidoResponse pagar(Long id) { Pedido p = buscarEntidade(id); if (p.getStatus() == StatusPedido.CANCELADO) throw new BusinessException("Pedido cancelado não pode ser pago"); p.setStatus(StatusPedido.PAGO); return toResponse(repository.save(p)); }
    @Transactional public PedidoResponse cancelar(Long id) { Pedido p = buscarEntidade(id); if (p.getStatus() == StatusPedido.PAGO) throw new BusinessException("Pedido pago não pode ser cancelado"); p.setStatus(StatusPedido.CANCELADO); return toResponse(repository.save(p)); }
    @Transactional public PedidoResponse alterarStatus(Long id, StatusPedidoRequest request) { Pedido p = buscarEntidade(id); p.setStatus(request.status()); return toResponse(repository.save(p)); }
    private PedidoResponse toResponse(Pedido p) {
        List<ItemPedidoResponse> itens = p.getItens().stream().map(i -> new ItemPedidoResponse(i.getProduto().getId(), i.getProduto().getNome(), i.getQuantidade(), i.getPrecoUnitario(), i.getSubtotal())).toList();
        return new PedidoResponse(p.getId(), p.getCliente().getId(), p.getCliente().getNome(), p.getDataPedido(), p.getStatus(), p.getTotal(), itens);
    }
}
