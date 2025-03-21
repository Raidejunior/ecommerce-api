package com.ecommerce.domain.repositories;

import com.ecommerce.application.dto.PedidoDetalhadoDTO;
import com.ecommerce.domain.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, String> {
    List<Pedido> findByUsuarioId(String usuarioId);

    @Query("""
    SELECT new com.ecommerce.application.dto.PedidoDetalhadoDTO(
        p.id, p.status, u.nomeUsuario, p.dataCriacao, p.dataAtualizacao,
        COUNT(pi.id), SUM(pi.quantidade),
        CASE WHEN p.status = 'PENDENTE' THEN SUM(pr.preco * pi.quantidade) ELSE p.precoTotalRegistrado END
    )
    FROM Pedido p
    INNER JOIN p.itens pi
    INNER JOIN pi.produto pr
    INNER JOIN p.usuario u
    WHERE u.nomeUsuario = :nomeUsuario
    GROUP BY p.id, p.status, u.nomeUsuario, p.dataCriacao, p.dataAtualizacao, p.precoTotalRegistrado
    ORDER BY CASE WHEN p.status = 'PENDENTE' THEN SUM(pr.preco * pi.quantidade) ELSE p.precoTotalRegistrado END DESC
""")
    List<PedidoDetalhadoDTO> buscarPedidosUsuario(@Param("nomeUsuario") String nomeUsuario);


    @Query("""
    SELECT new com.ecommerce.application.dto.PedidoDetalhadoDTO(
        p.id, p.status, u.nomeUsuario, p.dataCriacao, p.dataAtualizacao,
        COUNT(pi.id), SUM(pi.quantidade),
        CASE WHEN p.status = 'PENDENTE' THEN SUM(pr.preco * pi.quantidade) ELSE p.precoTotalRegistrado END
    )
    FROM Pedido p
    INNER JOIN p.itens pi
    INNER JOIN pi.produto pr
    INNER JOIN p.usuario u
    GROUP BY p.id, p.status, u.nomeUsuario, p.dataCriacao, p.dataAtualizacao, p.precoTotalRegistrado
    ORDER BY CASE WHEN p.status = 'PENDENTE' THEN SUM(pr.preco * pi.quantidade) ELSE p.precoTotalRegistrado END DESC
""")
    List<PedidoDetalhadoDTO> buscarTodosPedidos();


    @Query("""
    SELECT new com.ecommerce.application.dto.PedidoDetalhadoDTO(
        p.id, p.status, u.nomeUsuario, p.dataCriacao, p.dataAtualizacao,
        COUNT(pi.id), SUM(pi.quantidade),
        CASE WHEN p.status = 'PENDENTE' THEN SUM(pr.preco * pi.quantidade) ELSE p.precoTotalRegistrado END
    )
    FROM Pedido p
    INNER JOIN p.itens pi
    INNER JOIN pi.produto pr
    INNER JOIN p.usuario u
    WHERE p.id = :pedidoId
    GROUP BY p.id, p.status, u.nomeUsuario, p.dataCriacao, p.dataAtualizacao, p.precoTotalRegistrado
    ORDER BY CASE WHEN p.status = 'PENDENTE' THEN SUM(pr.preco * pi.quantidade) ELSE p.precoTotalRegistrado END DESC
""")
    List<PedidoDetalhadoDTO> buscarPedido(@Param("pedidoId") String pedidoId);

}
