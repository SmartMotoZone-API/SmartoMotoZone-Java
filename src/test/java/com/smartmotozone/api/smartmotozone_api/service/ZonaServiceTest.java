package com.smartmotozone.api.smartmotozone_api.service;

import com.smartmotozone.api.smartmotozone_api.dto.ZonaDTO;
import com.smartmotozone.api.smartmotozone_api.exception.ResourceNotFoundException;
import com.smartmotozone.api.smartmotozone_api.model.Zona;
import com.smartmotozone.api.smartmotozone_api.repository.ZonaRepository;
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
class ZonaServiceTest {

    @Mock
    private ZonaRepository zonaRepository;

    @InjectMocks
    private ZonaService zonaService;

    private Zona zona;
    private ZonaDTO zonaDTO;

    @BeforeEach
    void setUp() {
        zonaDTO = new ZonaDTO(1L, "Z01", "Zona de Entrada");
        zona = new Zona(1L, "Z01", "Zona de Entrada", null);
    }

    @Test
    void testSalvarZona() {
        when(zonaRepository.save(any(Zona.class))).thenReturn(zona);
        Zona zonaSalva = zonaService.salvar(zonaDTO);
        assertThat(zonaSalva).isNotNull();
        assertThat(zonaSalva.getCodigo()).isEqualTo("Z01");
        verify(zonaRepository, times(1)).save(any(Zona.class));
    }

    @Test
    void testBuscarPorId_QuandoEncontrado() {
        when(zonaRepository.findById(1L)).thenReturn(Optional.of(zona));
        Zona zonaEncontrada = zonaService.buscarPorId(1L);
        assertThat(zonaEncontrada).isNotNull();
        assertThat(zonaEncontrada.getId()).isEqualTo(1L);
        verify(zonaRepository, times(1)).findById(1L);
    }

    @Test
    void testBuscarPorId_QuandoNaoEncontrado() {
        when(zonaRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            zonaService.buscarPorId(99L);
        });
        verify(zonaRepository, times(1)).findById(99L);
    }
}