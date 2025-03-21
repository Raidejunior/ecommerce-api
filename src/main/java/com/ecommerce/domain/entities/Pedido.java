package com.ecommerce.domain.entities;

import com.ecommerce.domain.enums.StatusPedido;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @Column(columnDefinition = "CHAR(36)", updatable = false, nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status;

    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "preco_total_registrado", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoTotalRegistrado = BigDecimal.ZERO;

    public Pedido() {
        this.id = UUID.randomUUID().toString();
        this.status = StatusPedido.PENDENTE;
    }

    public Pedido(Usuario usuario, List<ItemPedido> itens) {
        this();
        this.usuario = usuario;
        this.itens = itens;
    }

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public BigDecimal getPrecoTotalRegistrado() {
        return precoTotalRegistrado;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public void setPrecoTotalRegistrado(BigDecimal precoTotalRegistrado) {
        this.precoTotalRegistrado = precoTotalRegistrado;
    }

    public BigDecimal getPrecoTotalAtualizado() {
        return itens == null || itens.isEmpty()
                ? BigDecimal.ZERO
                : itens.stream()
                .map(ItemPedido::getPrecoTotalAtualizado)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
