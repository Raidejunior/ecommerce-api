package com.ecommerce.application.services;

import com.ecommerce.domain.entities.Usuario;
import com.ecommerce.domain.enums.PerfilUsuario;
import com.ecommerce.domain.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario cadastrarUsuario(Usuario usuario, PerfilUsuario perfil) {
        if (usuarioRepository.findByNomeUsuario(usuario.getNomeUsuario()).isPresent()) {
            throw new IllegalArgumentException("Usuário já existe!");
        }

        usuario.setPerfil(perfil);

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public Usuario cadastrarAdmin(Usuario usuario) {
        return cadastrarUsuario(usuario, PerfilUsuario.ADMIN);
    }

    public Usuario cadastrarUser(Usuario usuario) {
        return cadastrarUsuario(usuario, PerfilUsuario.USER);
    }

    public Optional<Usuario> buscarPorNomeUsuario(String nomeUsuario) {
        return usuarioRepository.findByNomeUsuario(nomeUsuario);
    }

    public boolean validarSenha(String senhaDigitada, String senhaCriptografada) {
        return passwordEncoder.matches(senhaDigitada, senhaCriptografada);
    }
}
