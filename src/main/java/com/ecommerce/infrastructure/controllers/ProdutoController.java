package com.ecommerce.infrastructure.controllers;

import com.ecommerce.application.dto.ProdutoDTO;
import com.ecommerce.application.services.ProdutoService;
import com.ecommerce.domain.entities.Produto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping()
    public ResponseEntity<Produto> cadastrarProduto(@RequestBody ProdutoDTO produtoDTO) {
        return ResponseEntity.ok(produtoService.cadastrarProduto(produtoDTO));
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        return ResponseEntity.ok(produtoService.listarProdutos());
    }

    @PostMapping("/buscar")
    public ResponseEntity<Produto> buscarProdutoPorId(@RequestBody ProdutoDTO produtoDTO) {
        return ResponseEntity.ok(produtoService.buscarProdutoPorId(produtoDTO.id()));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Produto> atualizarProduto(@RequestBody ProdutoDTO produtoDTO) {
        return ResponseEntity.ok(produtoService.atualizarProduto(produtoDTO));
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deletarProduto(@RequestBody ProdutoDTO produtoDTO) {
        produtoService.deletarProduto(produtoDTO.id());
        return ResponseEntity.noContent().build();
    }
}
