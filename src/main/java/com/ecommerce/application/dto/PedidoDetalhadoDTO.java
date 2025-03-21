package com.ecommerce.application.dto;

import com.ecommerce.domain.enums.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PedidoDetalhadoDTO {
    private final String pedidoId;
    private final StatusPedido status;
    private final String usuario;
    private final LocalDateTime pedidoData;
    private final LocalDateTime pedidoDataAtualizacao;
    private final Long quantidadeItens;
    private final Long totalQuantidadeItens;
    private final BigDecimal valorTotal;

    public PedidoDetalhadoDTO(String pedidoId, StatusPedido status, String usuario,
                              LocalDateTime pedidoData, LocalDateTime pedidoDataAtualizacao,
                              Long quantidadeItens, Long totalQuantidadeItens, BigDecimal valorTotal) {
        this.pedidoId = pedidoId;
        this.status = status;
        this.usuario = usuario;
        this.pedidoData = pedidoData;
        this.pedidoDataAtualizacao = pedidoDataAtualizacao;
        this.quantidadeItens = quantidadeItens;
        this.totalQuantidadeItens = totalQuantidadeItens;
        this.valorTotal = valorTotal;
    }

    public String getPedidoId() {
        return pedidoId;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public String getUsuario() {
        return usuario;
    }

    public LocalDateTime getPedidoData() {
        return pedidoData;
    }

    public LocalDateTime getPedidoDataAtualizacao() {
        return pedidoDataAtualizacao;
    }

    public Long getQuantidadeItens() {
        return quantidadeItens;
    }

    public Long getTotalQuantidadeItens() {
        return totalQuantidadeItens;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }
}
