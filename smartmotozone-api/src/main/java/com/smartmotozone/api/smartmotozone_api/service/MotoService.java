package com.smartmotozone.api.smartmotozone_api.service;

import com.smartmotozone.api.smartmotozone_api.dto.MotoDTO;
import com.smartmotozone.api.smartmotozone_api.exception.ResourceNotFoundException;
import com.smartmotozone.api.smartmotozone_api.model.Moto;
import com.smartmotozone.api.smartmotozone_api.model.Zona;
import com.smartmotozone.api.smartmotozone_api.repository.MotoRepository;
import com.smartmotozone.api.smartmotozone_api.repository.ZonaRepository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoService {

    private final MotoRepository motoRepository;
    private final ZonaRepository zonaRepository;

    public MotoService(MotoRepository motoRepository, ZonaRepository zonaRepository) {
        this.motoRepository = motoRepository;
        this.zonaRepository = zonaRepository;
    }

    @Cacheable(value = "motos", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<Moto> listarTodos(Pageable pageable) {
        return motoRepository.findAll(pageable);
    }

    @Cacheable(value = "motoPormodelo", key = "#modelo + '-' + #pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<Moto> buscarPorModelo(String modelo, Pageable pageable) {
        return motoRepository.findByModeloContainingIgnoreCase(modelo, pageable);
    }

    @Cacheable(value = "motoPorZonacodigo", key = "#codigoZona")
    public List<Moto> buscarPorZonaCodigo(String codigoZona) {
        return motoRepository.findByZonaCodigo(codigoZona);
    }

    @Cacheable(value = "motoPorId", key = "#id")
    public Moto buscarPorId(Long id) {
        return motoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada"));
    }

    @CacheEvict(value = {"motos", "motoPormodelo", "motoPorZonacodigo", "motoPorId"}, allEntries = true)
    public Moto salvar(MotoDTO dto) {
        Zona zona = zonaRepository.findById(dto.zonaId())
                .orElseThrow(() -> new ResourceNotFoundException("Zona não encontrada"));

        Moto moto = new Moto(null, dto.modelo(), dto.placa(), dto.status(), zona);
        return motoRepository.save(moto);
    }

    @CacheEvict(value = {"motos", "motoPormodelo", "motoPorZonacodigo", "motoPorId"}, allEntries = true)
    public Moto atualizar(Long id, MotoDTO dto) {
        Moto moto = buscarPorId(id);
        Zona zona = zonaRepository.findById(dto.zonaId())
                .orElseThrow(() -> new ResourceNotFoundException("Zona não encontrada"));

        moto.setModelo(dto.modelo());
        moto.setPlaca(dto.placa());
        moto.setStatus(dto.status());
        moto.setZona(zona);

        return motoRepository.save(moto);
    }

    @CacheEvict(value = {"motos", "motoPormodelo", "motoPorZonacodigo", "motoPorId"}, allEntries = true)
    public void deletar(Long id) {
        if (!motoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Moto não encontrada para exclusão");
        }
        motoRepository.deleteById(id);
    }
}
