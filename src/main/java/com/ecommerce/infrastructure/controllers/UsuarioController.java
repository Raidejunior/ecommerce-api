package com.ecommerce.infrastructure.controllers;

import com.ecommerce.application.services.UsuarioService;
import com.ecommerce.domain.entities.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastrarAdmin")
    public ResponseEntity<Usuario> cadastrarAdmin(@RequestBody @Valid Usuario usuario) {
        return ResponseEntity.ok(usuarioService.cadastrarAdmin(usuario));
    }

    @PostMapping("/cadastrarUser")
    public ResponseEntity<Usuario> cadastrarUser(@RequestBody @Valid Usuario usuario) {
        return ResponseEntity.ok(usuarioService.cadastrarUser(usuario));
    }
}