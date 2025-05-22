package com.smartmotozone.api.smartmotozone_api.controller;

import com.smartmotozone.api.smartmotozone_api.dto.MovimentacaoDTO;
import com.smartmotozone.api.smartmotozone_api.model.Movimentacao;
import com.smartmotozone.api.smartmotozone_api.service.MovimentacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movimentacoes")
@RequiredArgsConstructor
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    @PostMapping
    public Movimentacao criar(@RequestBody @Valid MovimentacaoDTO dto) {
        return movimentacaoService.salvar(dto);
    }

    @GetMapping
    public Page<Movimentacao> listarTodos(Pageable pageable) {
        return movimentacaoService.listarTodas(pageable);
    }

    @GetMapping("/moto/{motoId}")
    public Page<Movimentacao> listarPorMoto(@PathVariable Long motoId, Pageable pageable) {
        return movimentacaoService.buscarPorMoto(motoId, pageable);
    }
}
