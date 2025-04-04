package com.ecommerce.domain.repositories;

import com.ecommerce.domain.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProdutoRepository extends JpaRepository<Produto, String> {
}
