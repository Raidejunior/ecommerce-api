package com.ecommerce.application.dto;

import java.util.List;

public record PedidoDTO(String id, List<ItemPedidoDTO> itens) {}
