package com.ecommerce.infrastructure.controllers;

import com.ecommerce.application.dto.PedidoDTO;
import com.ecommerce.application.dto.PedidoDetalhadoDTO;
import com.ecommerce.application.services.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<List<PedidoDetalhadoDTO>> criarPedido(@RequestBody PedidoDTO pedidoDTO) {
        return ResponseEntity.ok(pedidoService.criarPedido(pedidoDTO));
    }

    @PostMapping("/pagar")
    public ResponseEntity<List<PedidoDetalhadoDTO>> pagarPedido(@RequestBody PedidoDTO pedidoDTO) {
        return ResponseEntity.ok(pedidoService.pagarPedido(pedidoDTO.id()));
    }

    @GetMapping
    public ResponseEntity<List<PedidoDetalhadoDTO>> listarPedidosUsuario() {
        return ResponseEntity.ok(pedidoService.listarPedidosUsuario());
    }

    @GetMapping("/todos")
    public ResponseEntity<List<PedidoDetalhadoDTO>> listarTodosOsPedidos() {
        return ResponseEntity.ok(pedidoService.listarTodosOsPedidos());
    }
}
