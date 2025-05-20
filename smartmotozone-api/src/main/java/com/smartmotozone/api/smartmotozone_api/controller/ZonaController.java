package com.smartmotozone.api.smartmotozone_api.controller;

import com.smartmotozone.api.smartmotozone_api.dto.ZonaDTO;
import com.smartmotozone.api.smartmotozone_api.model.Zona;
import com.smartmotozone.api.smartmotozone_api.service.ZonaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/zonas")
public class ZonaController {

    private final ZonaService zonaService;

    public ZonaController(ZonaService zonaService) {
        this.zonaService = zonaService;
    }

    @GetMapping
    public ResponseEntity<Page<Zona>> listar(@PageableDefault Pageable pageable) {
        Page<Zona> zonas = zonaService.listarTodos(pageable);
        return ResponseEntity.status(OK).body(zonas);
    }

    @PostMapping
    public ResponseEntity<Zona> criar(@RequestBody @Valid ZonaDTO dto) {
        Zona zona = zonaService.salvar(dto);
        return ResponseEntity.status(CREATED).body(zona);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Zona> buscarPorId(@PathVariable Long id) {
        Zona zona = zonaService.buscarPorId(id);
        return ResponseEntity.status(OK).body(zona);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Zona> atualizar(@PathVariable Long id, @RequestBody @Valid ZonaDTO dto) {
        Zona zonaAtualizada = zonaService.atualizar(id, dto);
        return ResponseEntity.status(OK).body(zonaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        zonaService.deletar(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Zona>> buscarPorDescricao(@RequestParam String descricao) {
    var zonas = zonaService.buscarPorDescricao(descricao);
    return ResponseEntity.ok(zonas);
}

}
