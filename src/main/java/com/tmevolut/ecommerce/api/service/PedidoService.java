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

    public PedidoService(PedidoRepository repository, ClienteService clienteService, ProdutoService produtoService) {
        this.repository = repository;
        this.clienteService = clienteService;
        this.produtoService = produtoService;
    }

    @Transactional
    public PedidoResponse criar(PedidoRequest request) {
        if (request.itens() == null || request.itens().isEmpty()) {
            throw new BusinessException("O pedido deve conter pelo menos um item");
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(clienteService.buscarEntidade(request.clienteId()));
        pedido.setStatus(StatusPedido.ABERTO);

        BigDecimal total = BigDecimal.ZERO;

        for (ItemPedidoRequest itemRequest : request.itens()) {
            Produto produto = produtoService.buscarEntidade(itemRequest.produtoId());
            produto.reduzirEstoque(itemRequest.quantidade());

            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(itemRequest.quantidade());
            item.setPrecoUnitario(produto.getPreco());
            item.setSubtotal(produto.getPreco().multiply(BigDecimal.valueOf(itemRequest.quantidade())));

            pedido.addItem(item);
            total = total.add(item.getSubtotal());
        }

        pedido.setTotal(total);
        return toResponse(repository.save(pedido));
    }

    @Transactional(readOnly = true)
    public Page<PedidoResponse> listar(StatusPedido status, Pageable pageable) {
        Page<Pedido> page = (status == null)
                ? repository.findByDeletedAtIsNull(pageable)
                : repository.findByStatusAndDeletedAtIsNull(status, pageable);
        return page.map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public Pedido buscarEntidade(Long id) {
        return repository.findById(id)
                .filter(p -> p.getDeletedAt() == null)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
    }

    @Transactional(readOnly = true)
    public PedidoResponse buscar(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional
    public PedidoResponse pagar(Long id) {
        Pedido p = buscarEntidade(id);
        if (p.getStatus() == StatusPedido.CANCELADO) {
            throw new BusinessException("Pedido cancelado não pode ser pago");
        }
        p.setStatus(StatusPedido.PAGO);
        return toResponse(repository.save(p));
    }

    @Transactional
    public PedidoResponse cancelar(Long id) {
        Pedido p = buscarEntidade(id);
        if (p.getStatus() == StatusPedido.PAGO) {
            throw new BusinessException("Pedido pago não pode ser cancelado");
        }
        p.setStatus(StatusPedido.CANCELADO);
        for (ItemPedido item : p.getItens()) {
            item.getProduto().adicionarEstoque(item.getQuantidade());
        }
        return toResponse(repository.save(p));
    }

    //  NOVO MÉTODO ADICIONADO PARA CORRIGIR O CONTROLLER //
    @Transactional
    public PedidoResponse alterarStatus(Long id, StatusPedidoRequest request) {
        Pedido p = buscarEntidade(id);

        if (p.getStatus() == StatusPedido.CANCELADO) {
            throw new BusinessException("Não é possível alterar o status de um pedido cancelado.");
        }

        p.setStatus(request.status());
        return toResponse(repository.save(p));
    }

    private PedidoResponse toResponse(Pedido p) {
        List<ItemPedidoResponse> itens = p.getItens().stream()
                .map(i -> new ItemPedidoResponse(
                        i.getProduto().getId(),
                        i.getProduto().getNome(),
                        i.getQuantidade(),
                        i.getPrecoUnitario(),
                        i.getSubtotal()))
                .toList();

        return new PedidoResponse(
                p.getId(),
                p.getCliente().getId(),
                p.getCliente().getNome(),
                p.getDataPedido(),
                p.getStatus(),
                p.getTotal(),
                itens);
    }
}