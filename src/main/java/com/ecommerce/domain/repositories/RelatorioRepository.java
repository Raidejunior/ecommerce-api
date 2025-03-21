package com.ecommerce.domain.repositories;

import com.ecommerce.application.dto.TicketMedioDTO;
import com.ecommerce.application.dto.TopCompradoresDTO;
import com.ecommerce.domain.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RelatorioRepository extends JpaRepository<Pedido, String> {

    @Query(value = """
            SELECT u.id, u.nome_usuario, COUNT(p.id) AS total_pedidos, SUM(p.preco_total_registrado) AS total_gasto
            FROM usuarios u
            JOIN pedidos p ON u.id = p.usuario_id
            WHERE p.status = 'PAGO'
            GROUP BY u.id, u.nome_usuario
            ORDER BY total_gasto DESC
            LIMIT 5
            """, nativeQuery = true)
    List<TopCompradoresDTO> topUsuariosCompradores();

    @Query(value = """
            SELECT u.id, u.nome_usuario, ROUND(AVG(p.preco_total_registrado), 2) AS ticket_medio
            FROM usuarios u
            JOIN pedidos p ON u.id = p.usuario_id
            WHERE p.status = 'PAGO'
            GROUP BY u.id, u.nome_usuario
            ORDER BY ticket_medio DESC
            """, nativeQuery = true)
    List<TicketMedioDTO> ticketMedioPorUsuario();

    @Query(value = """
        SELECT COALESCE(SUM(p.preco_total_registrado), 0) AS total_faturado
        FROM pedidos p
        WHERE p.status = 'PAGO' 
        AND MONTH(p.data_criacao) = :mes
        AND YEAR(p.data_criacao) = :ano
        """, nativeQuery = true)
    BigDecimal totalFaturadoNoMes(int mes, int ano);
}
