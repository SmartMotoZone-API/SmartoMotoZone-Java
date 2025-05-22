package com.smartmotozone.api.smartmotozone_api.service;

import com.smartmotozone.api.smartmotozone_api.dto.MovimentacaoDTO;
import com.smartmotozone.api.smartmotozone_api.model.Moto;
import com.smartmotozone.api.smartmotozone_api.model.Movimentacao;
import com.smartmotozone.api.smartmotozone_api.model.Zona;
import com.smartmotozone.api.smartmotozone_api.repository.MotoRepository;
import com.smartmotozone.api.smartmotozone_api.repository.MovimentacaoRepository;
import com.smartmotozone.api.smartmotozone_api.repository.ZonaRepository;
import com.smartmotozone.api.smartmotozone_api.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final MotoRepository motoRepository;
    private final ZonaRepository zonaRepository;

    @CacheEvict(value = {"movimentacao", "movimentacaoporMoto"}, allEntries = true)
    public Movimentacao salvar(MovimentacaoDTO dto) {
        Moto moto = motoRepository.findById(dto.motoId())
                .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada"));

        Zona zonaOrigem = zonaRepository.findById(dto.zonaOrigemId())
                .orElseThrow(() -> new ResourceNotFoundException("Zona de origem não encontrada"));

        Zona zonaDestino = zonaRepository.findById(dto.zonaDestinoId())
                .orElseThrow(() -> new ResourceNotFoundException("Zona de destino não encontrada"));

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setMoto(moto);
        movimentacao.setZonaOrigem(zonaOrigem);
        movimentacao.setZonaDestino(zonaDestino);
        movimentacao.setDescricao(dto.descricao());
        movimentacao.setDataHora(dto.dataHora());

        moto.setZona(zonaDestino);
        motoRepository.save(moto);

        return movimentacaoRepository.save(movimentacao);
    }

    @Cacheable(value = "movimentacao", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<Movimentacao> listarTodas(Pageable pageable) {
        return movimentacaoRepository.findAll(pageable);
    }

    @Cacheable(value = "movimentacaoporMoto", key = "#motoId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<Movimentacao> buscarPorMoto(Long motoId, Pageable pageable) {
        return movimentacaoRepository.findByMotoId(motoId, pageable);
    }
}


