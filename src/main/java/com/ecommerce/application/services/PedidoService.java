package com.ecommerce.application.services;

import com.ecommerce.application.dto.PedidoDTO;
import com.ecommerce.application.dto.PedidoDetalhadoDTO;
import com.ecommerce.domain.entities.ItemPedido;
import com.ecommerce.domain.entities.Pedido;
import com.ecommerce.domain.entities.Produto;
import com.ecommerce.domain.entities.Usuario;
import com.ecommerce.domain.enums.StatusPedido;
import com.ecommerce.domain.repositories.ItemPedidoRepository;
import com.ecommerce.domain.repositories.PedidoRepository;
import com.ecommerce.domain.repositories.ProdutoRepository;
import com.ecommerce.domain.repositories.UsuarioRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    @Lazy
    private final PedidoService self; // Auto-injetado com proxy para usar REQUIRES_NEW

    public PedidoService(PedidoRepository pedidoRepository,
                         ProdutoRepository produtoRepository,
                         UsuarioRepository usuarioRepository,
                         ItemPedidoRepository itemPedidoRepository,
                         @Lazy PedidoService self) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
        this.itemPedidoRepository = itemPedidoRepository;
        this.self = self;
    }

    @Transactional
    public List<PedidoDetalhadoDTO> criarPedido(PedidoDTO pedidoDTO) {
        String nomeUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByNomeUsuario(nomeUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Pedido pedido = new Pedido(usuario, List.of());
        pedidoRepository.save(pedido);

        List<ItemPedido> itens = pedidoDTO.itens().stream().map(itemDTO -> {
            Produto produto = produtoRepository.findById(itemDTO.produtoId())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

            if (produto.getQuantidadeEmEstoque() < itemDTO.quantidade()) {
                throw new IllegalArgumentException("Estoque insuficiente para o produto: " + produto.getNome());
            }

            return new ItemPedido(pedido, produto, itemDTO.quantidade());
        }).collect(Collectors.toList());

        pedido.setItens(itens);
        pedido.setPrecoTotalRegistrado(pedido.getPrecoTotalAtualizado());
        pedidoRepository.save(pedido);

        return pedidoRepository.buscarPedido(pedido.getId());
    }

    @Transactional
    public List<PedidoDetalhadoDTO> pagarPedido(String pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));

        if (pedido.getStatus() != StatusPedido.PENDENTE) {
            throw new IllegalArgumentException("Somente pedidos PENDENTES podem ser pagos.");
        }

        for (ItemPedido item : pedido.getItens()) {
            Produto produto = item.getProduto();
            item.setPrecoUnitarioRegistrado(item.getPrecoUnitarioAtualizado());
            item.setPrecoTotalRegistrado(item.getPrecoTotalAtualizado());

            if (produto.getQuantidadeEmEstoque() < item.getQuantidade()) {
                pedido.setStatus(StatusPedido.CANCELADO);
                self.cancelarPedido(pedido);
                throw new IllegalArgumentException("Pedido cancelado! Estoque insuficiente para o produto: " + produto.getNome());
            }

            produto.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque() - item.getQuantidade());
            produtoRepository.save(produto);
        }

        pedido.setStatus(StatusPedido.PAGO);
        pedido.setPrecoTotalRegistrado(pedido.getPrecoTotalAtualizado());
        pedidoRepository.save(pedido);

        return pedidoRepository.buscarPedido(pedidoId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cancelarPedido(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    @Transactional(readOnly = true)
    public List<PedidoDetalhadoDTO> listarPedidosUsuario() {
        String nomeUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByNomeUsuario(nomeUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        return pedidoRepository.buscarPedidosUsuario(nomeUsuario);
    }

    @Transactional(readOnly = true)
    public List<PedidoDetalhadoDTO> listarTodosOsPedidos() {
        return pedidoRepository.buscarTodosPedidos();
    }
}
