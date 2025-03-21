package com.ecommerce.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "pedido_itens")
public class ItemPedido {
    @Id
    @Column(columnDefinition = "CHAR(36)", updatable = false, nullable = false)
    private String id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pedido_id", nullable = false)
    @JsonIgnore
    private Pedido pedido;

    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(name = "preco_unitario_registrado", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitarioRegistrado;

    @Column(nullable = false, precision = 10, scale = 2, name= "preco_total_registrado")
    private BigDecimal precoTotalRegistrado; // Armazena o preço da quantidade dos produtos na compra

    public ItemPedido() {
        this.id = UUID.randomUUID().toString();
    }

    public ItemPedido(Pedido pedido, Produto produto, Integer quantidade) {
        this();
        this.pedido = pedido;
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitarioRegistrado = produto.getPreco();
        this.precoTotalRegistrado = getPrecoTotalAtualizado(); // Armazena o preço no momento da compra
    }

    public void setPrecoUnitarioRegistrado(BigDecimal precoUnitarioRegistrado) {
        this.precoUnitarioRegistrado = precoUnitarioRegistrado;
    }

    public void setPrecoTotalRegistrado(BigDecimal precoTotalRegistrado) {
        this.precoTotalRegistrado = precoTotalRegistrado;
    }

    public String getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public BigDecimal getPrecoTotalRegistrado() {
        return precoTotalRegistrado;
    }

    public BigDecimal getPrecoTotalAtualizado() {
        return produto.getPreco().multiply(BigDecimal.valueOf(quantidade));
    }

    public BigDecimal getPrecoUnitarioAtualizado() {
        return produto.getPreco();
    }
}
