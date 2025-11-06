package com.smartmotozone.api.smartmotozone_api.service;

import com.smartmotozone.api.smartmotozone_api.dto.UsuarioDTO;
import com.smartmotozone.api.smartmotozone_api.exception.BusinessException;
import com.smartmotozone.api.smartmotozone_api.exception.ResourceNotFoundException;
import com.smartmotozone.api.smartmotozone_api.model.Usuario;
import com.smartmotozone.api.smartmotozone_api.repository.UsuarioRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Cacheable(value = "usuarios", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<Usuario> listarTodos(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @CacheEvict(value = {"usuarios", "usuariosPorId", "usuarioPorLogin"}, allEntries = true)
    public Usuario salvar(UsuarioDTO dto) {
        if (usuarioRepository.findByLogin(dto.login()).isPresent()) {
            throw new BusinessException("Login já está em uso");
        }

        String senhaHasheada = passwordEncoder.encode(dto.senha());
        Usuario usuario = new Usuario(null, dto.login(), dto.nome(), dto.perfil(), senhaHasheada, dto.email(), null);
        return usuarioRepository.save(usuario);
    }

    @Cacheable(value = "usuariosPorId", key = "#id")
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    @CacheEvict(value = {"usuarios", "usuariosPorId", "usuarioPorLogin"}, allEntries = true)
    public Usuario atualizar(Long id, UsuarioDTO dto) {
        Usuario usuario = buscarPorId(id);

        String senhaHasheada = passwordEncoder.encode(dto.senha());

        usuario.setNome(dto.nome());
        usuario.setPerfil(dto.perfil());
        usuario.setLogin(dto.login());
        usuario.setSenha(senhaHasheada);
        usuario.setEmail(dto.email());
        return usuarioRepository.save(usuario);
    }

    @CacheEvict(value = {"usuarios", "usuariosPorId", "usuarioPorLogin"}, allEntries = true)
    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Cacheable(value = "usuarioPorLogin", key = "#login")
    public Usuario buscarPorLogin(String login) {
        return usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com login '" + login + "' não encontrado"));
    }
}