package com.smartmotozone.api.smartmotozone_api.service;

import com.smartmotozone.api.smartmotozone_api.dto.FuncionarioDTO;
import com.smartmotozone.api.smartmotozone_api.exception.ResourceNotFoundException;
import com.smartmotozone.api.smartmotozone_api.model.Funcionario;
import com.smartmotozone.api.smartmotozone_api.repository.FuncionarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @InjectMocks
    private FuncionarioService funcionarioService;

    private Funcionario funcionario;
    private FuncionarioDTO funcionarioDTO;

    @BeforeEach
    void setUp() {
        funcionarioDTO = new FuncionarioDTO(1L, "Kaio Cumpian", "Desenvolvedor");
        funcionario = new Funcionario(1L, "Kaio Cumpian", "Desenvolvedor");
    }

    @Test
    void testSalvarFuncionario() {
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionario);
        Funcionario funcionarioSalvo = funcionarioService.salvar(funcionarioDTO);
        assertThat(funcionarioSalvo).isNotNull();
        assertThat(funcionarioSalvo.getNome()).isEqualTo("Kaio Cumpian");
        verify(funcionarioRepository, times(1)).save(any(Funcionario.class));
    }

    @Test
    void testBuscarPorId_QuandoEncontrado() {
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));
        Funcionario funcionarioEncontrado = funcionarioService.buscarPorId(1L);
        assertThat(funcionarioEncontrado).isNotNull();
        assertThat(funcionarioEncontrado.getId()).isEqualTo(1L);
        verify(funcionarioRepository, times(1)).findById(1L);
    }

    @Test
    void testBuscarPorId_QuandoNaoEncontrado() {
        when(funcionarioRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            funcionarioService.buscarPorId(99L);
        });
        verify(funcionarioRepository, times(1)).findById(99L);
    }
}