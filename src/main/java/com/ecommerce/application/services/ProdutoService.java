package com.ecommerce.application.services;

import com.ecommerce.application.dto.ProdutoDTO;
import com.ecommerce.domain.entities.Produto;
import com.ecommerce.domain.repositories.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto cadastrarProduto(ProdutoDTO produtoDTO) {
        Produto produto = new Produto(
                produtoDTO.nome(),
                produtoDTO.descricao(),
                produtoDTO.preco(),
                produtoDTO.categoria(),
                produtoDTO.quantidadeEmEstoque()
        );
        return produtoRepository.save(produto);
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public Produto buscarProdutoPorId(String id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
    }

    public Produto atualizarProduto(ProdutoDTO produtoDTO) {
        Produto produto = produtoRepository.findById(produtoDTO.id())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        produto.setNome(produtoDTO.nome());
        produto.setDescricao(produtoDTO.descricao());
        produto.setPreco(produtoDTO.preco());
        produto.setCategoria(produtoDTO.categoria());
        produto.setQuantidadeEmEstoque(produtoDTO.quantidadeEmEstoque());

        return produtoRepository.save(produto);
    }

    public void deletarProduto(String id) {
        if (!produtoRepository.existsById(id)) {
            throw new IllegalArgumentException("Produto não encontrado");
        }
        produtoRepository.deleteById(id);
    }
}
