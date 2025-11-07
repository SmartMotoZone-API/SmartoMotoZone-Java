package com.smartmotozone.api.smartmotozone_api.service;

import com.smartmotozone.api.smartmotozone_api.dto.MotoDTO;
import com.smartmotozone.api.smartmotozone_api.exception.ResourceNotFoundException;
import com.smartmotozone.api.smartmotozone_api.model.Moto;
import com.smartmotozone.api.smartmotozone_api.model.Zona;
import com.smartmotozone.api.smartmotozone_api.repository.MotoRepository;
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
class MotoServiceTest {

    @Mock
    private MotoRepository motoRepository;

    @Mock
    private ZonaRepository zonaRepository;

    @InjectMocks
    private MotoService motoService;

    private Moto moto;
    private MotoDTO motoDTO;
    private Zona zona;

    @BeforeEach
    void setUp() {
        zona = new Zona(1L, "Z01", "Zona de Entrada", null);
        motoDTO = new MotoDTO(1L, "CG 160", "ABC1234", 1L, "Ativa");
        moto = new Moto(1L, "CG 160", "ABC1234", "Ativa", zona);
    }

    @Test
    void testSalvarMoto() {
        when(zonaRepository.findById(1L)).thenReturn(Optional.of(zona));
        when(motoRepository.save(any(Moto.class))).thenReturn(moto);
        Moto motoSalva = motoService.salvar(motoDTO);
        assertThat(motoSalva).isNotNull();
        assertThat(motoSalva.getModelo()).isEqualTo("CG 160");
        assertThat(motoSalva.getZona().getCodigo()).isEqualTo("Z01");
        verify(zonaRepository, times(1)).findById(1L);
        verify(motoRepository, times(1)).save(any(Moto.class));
    }

    @Test
    void testSalvarMoto_QuandoZonaNaoEncontrada() {
        when(zonaRepository.findById(99L)).thenReturn(Optional.empty());
        MotoDTO dtoComZonaInvalida = new MotoDTO(1L, "CG 160", "ABC1234", 99L, "Ativa");
        assertThrows(ResourceNotFoundException.class, () -> {
            motoService.salvar(dtoComZonaInvalida);
        });
        verify(motoRepository, never()).save(any(Moto.class));
    }

    @Test
    void testBuscarPorId_QuandoEncontrado() {
        when(motoRepository.findById(1L)).thenReturn(Optional.of(moto));
        Moto motoEncontrada = motoService.buscarPorId(1L);
        assertThat(motoEncontrada).isNotNull();
        assertThat(motoEncontrada.getId()).isEqualTo(1L);
        verify(motoRepository, times(1)).findById(1L);
    }

    @Test
    void testBuscarPorId_QuandoNaoEncontrado() {
        when(motoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            motoService.buscarPorId(99L);
        });
    }
}