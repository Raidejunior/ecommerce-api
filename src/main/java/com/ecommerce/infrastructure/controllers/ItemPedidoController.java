package com.ecommerce.infrastructure.controllers;

import com.ecommerce.application.services.ItemPedidoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/itens-pedido")
public class ItemPedidoController {
    private final ItemPedidoService itemPedidoService;

    public ItemPedidoController(ItemPedidoService itemPedidoService) {
        this.itemPedidoService = itemPedidoService;
    }
}