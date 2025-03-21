package com.ecommerce.infrastructure.controllers;

import com.ecommerce.application.dto.LoginDTO;
import com.ecommerce.application.services.UsuarioService;
import com.ecommerce.domain.entities.Usuario;
import com.ecommerce.infrastructure.config.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    public AuthController(UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorNomeUsuario(loginDTO.nomeUsuario());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Usuário não encontrado");
        }

        Usuario usuario = usuarioOpt.get();

        if (usuarioService.validarSenha(loginDTO.senha(), usuario.getSenha())) {
            String token = jwtUtil.generateToken(usuario.getNomeUsuario());
            return ResponseEntity.ok("Token JWT: " + token);
        } else {
            return ResponseEntity.status(401).body("Senha incorreta");
        }
    }
}
