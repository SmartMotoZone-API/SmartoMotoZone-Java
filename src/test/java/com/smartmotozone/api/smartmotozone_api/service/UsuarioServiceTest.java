package com.smartmotozone.api.smartmotozone_api.service;

import com.smartmotozone.api.smartmotozone_api.dto.UsuarioDTO;
import com.smartmotozone.api.smartmotozone_api.enums.UserRole;
import com.smartmotozone.api.smartmotozone_api.exception.BusinessException;
import com.smartmotozone.api.smartmotozone_api.exception.ResourceNotFoundException;
import com.smartmotozone.api.smartmotozone_api.model.Usuario;
import com.smartmotozone.api.smartmotozone_api.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        usuarioDTO = new UsuarioDTO(1L, "Kaio Admin", "ADMIN", "kaio", "senha123", "kaio@email.com");
        usuario = new Usuario(1L, "kaio", "Kaio Admin", UserRole.ADMIN, "senhaHasheada", "kaio@email.com");
    }

    @Test
    void testSalvarUsuario() {
        when(usuarioRepository.findByLogin("kaio")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("senha123")).thenReturn("senhaHasheada");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        Usuario usuarioSalvo = usuarioService.salvar(usuarioDTO);
        assertThat(usuarioSalvo).isNotNull();
        assertThat(usuarioSalvo.getLogin()).isEqualTo("kaio");
        assertThat(usuarioSalvo.getSenha()).isEqualTo("senhaHasheada");
        verify(usuarioRepository, times(1)).findByLogin("kaio");
        verify(passwordEncoder, times(1)).encode("senha123");
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testSalvarUsuario_QuandoLoginJaExiste() {
        when(usuarioRepository.findByLogin("kaio")).thenReturn(Optional.of(usuario));
        assertThrows(BusinessException.class, () -> {
            usuarioService.salvar(usuarioDTO);
        });
        verify(passwordEncoder, never()).encode(anyString());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void testBuscarPorId_QuandoEncontrado() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        Usuario usuarioEncontrado = usuarioService.buscarPorId(1L);
        assertThat(usuarioEncontrado).isNotNull();
        assertThat(usuarioEncontrado.getId()).isEqualTo(1L);
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void testBuscarPorId_QuandoNaoEncontrado() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.buscarPorId(99L);
        });
    }
}