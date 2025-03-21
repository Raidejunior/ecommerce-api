package com.ecommerce.application.services;

import com.ecommerce.application.dto.FiltroFaturamentoDTO;
import com.ecommerce.application.dto.TicketMedioDTO;
import com.ecommerce.application.dto.TopCompradoresDTO;
import com.ecommerce.domain.repositories.RelatorioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RelatorioService {
    private final RelatorioRepository relatorioRepository;

    public RelatorioService(RelatorioRepository relatorioRepository) {
        this.relatorioRepository = relatorioRepository;
    }

    public List<TopCompradoresDTO> topUsuariosCompradores() {
        return relatorioRepository.topUsuariosCompradores();
    }

    public List<TicketMedioDTO> ticketMedioPorUsuario() {
        return relatorioRepository.ticketMedioPorUsuario();
    }

    public BigDecimal totalFaturadoNoMes(FiltroFaturamentoDTO filtroFaturamentoDTO) {
        return relatorioRepository.totalFaturadoNoMes(filtroFaturamentoDTO.mes(), filtroFaturamentoDTO.ano());
    }
}
