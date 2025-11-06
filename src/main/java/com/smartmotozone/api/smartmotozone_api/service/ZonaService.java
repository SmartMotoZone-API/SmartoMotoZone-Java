package com.smartmotozone.api.smartmotozone_api.service;

import com.smartmotozone.api.smartmotozone_api.dto.ZonaDTO;
import com.smartmotozone.api.smartmotozone_api.exception.ResourceNotFoundException;
import com.smartmotozone.api.smartmotozone_api.model.Zona;
import com.smartmotozone.api.smartmotozone_api.repository.ZonaRepository;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ZonaService {

    private final ZonaRepository zonaRepository;

    public ZonaService(ZonaRepository zonaRepository) {
        this.zonaRepository = zonaRepository;
    }

    @Cacheable(value = "zonas", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<Zona> listarTodos(Pageable pageable) {
        return zonaRepository.findAll(pageable);
    }

    @CacheEvict(value = {"zonas", "zonasPorId"}, allEntries = true)
    public Zona salvar(ZonaDTO dto) {
        Zona zona = new Zona(null, dto.codigo(), dto.descricao(), null);
        return zonaRepository.save(zona);
    }

    @Cacheable(value = "zonasPorId", key = "#id")
    public Zona buscarPorId(Long id) {
        return zonaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zona não encontrada"));
    }

    @CacheEvict(value = {"zonas", "zonasPorId"}, allEntries = true)
    public Zona atualizar(Long id, ZonaDTO dto) {
        Zona zona = buscarPorId(id);
        zona.setCodigo(dto.codigo());
        zona.setDescricao(dto.descricao());
        return zonaRepository.save(zona);
    }

    @CacheEvict(value = {"zonas", "zonasPorId"}, allEntries = true)
    public void deletar(Long id) {
        zonaRepository.deleteById(id);
    }

    @Cacheable(value = "zonasPorDescricao", key = "#descricao")
    public List<Zona> buscarPorDescricao(String descricao) {
        return zonaRepository.findByDescricaoContainingIgnoreCase(descricao);
    }
}

