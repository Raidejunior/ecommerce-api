package com.ecommerce.application.services;

import com.ecommerce.domain.repositories.ItemPedidoRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemPedidoService {
    private final ItemPedidoRepository itemPedidoRepository;

    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
    }

}