package com.ecommerce.infrastructure.controllers;

import com.ecommerce.application.dto.FiltroFaturamentoDTO;
import com.ecommerce.application.dto.TicketMedioDTO;
import com.ecommerce.application.dto.TopCompradoresDTO;
import com.ecommerce.application.services.RelatorioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {
    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/top-compradores")
    public ResponseEntity<List<TopCompradoresDTO>> topUsuariosCompradores() {
        return ResponseEntity.ok(relatorioService.topUsuariosCompradores());
    }

    @GetMapping("/ticket-medio")
    public ResponseEntity<List<TicketMedioDTO>> ticketMedioPorUsuario() {
        return ResponseEntity.ok(relatorioService.ticketMedioPorUsuario());
    }

    @GetMapping("/faturamento")
    public ResponseEntity<BigDecimal> totalFaturadoNoMes(
            @RequestBody FiltroFaturamentoDTO filtroFaturamentoDTO
            ) {
        return ResponseEntity.ok(relatorioService.totalFaturadoNoMes(filtroFaturamentoDTO));
    }
}
