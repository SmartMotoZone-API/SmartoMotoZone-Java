package com.smartmotozone.api.smartmotozone_api.service;

import com.smartmotozone.api.smartmotozone_api.dto.ZonaDTO;
import com.smartmotozone.api.smartmotozone_api.exception.ResourceNotFoundException;
import com.smartmotozone.api.smartmotozone_api.model.Zona;
import com.smartmotozone.api.smartmotozone_api.repository.ZonaRepository;
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

    @Cacheable("zonas")
    public Page<Zona> listarTodos(Pageable pageable) {
        return zonaRepository.findAll(pageable);
    }

    public Zona salvar(ZonaDTO dto) {
        Zona zona = new Zona(null, dto.codigo(), dto.descricao(), null);
        return zonaRepository.save(zona);
    }

    public Zona buscarPorId(Long id) {
        return zonaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zona não encontrada"));

    }

    public void deletar(Long id) {
        zonaRepository.deleteById(id);
    }

    public Zona atualizar(Long id, ZonaDTO dto) {
        Zona zona = buscarPorId(id);
        zona.setCodigo(dto.codigo());
        zona.setDescricao(dto.descricao());
        return zonaRepository.save(zona);
    }
}
