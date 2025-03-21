package com.ecommerce.application.dto;

import com.ecommerce.domain.enums.Categoria;
import java.math.BigDecimal;

public record ProdutoDTO(
        String id,
        String nome,
        String descricao,
        BigDecimal preco,
        Categoria categoria,
        Integer quantidadeEmEstoque
) {}
