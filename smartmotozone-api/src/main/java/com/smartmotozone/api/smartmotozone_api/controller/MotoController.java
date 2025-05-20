package com.smartmotozone.api.smartmotozone_api.controller;

import com.smartmotozone.api.smartmotozone_api.dto.MotoDTO;
import com.smartmotozone.api.smartmotozone_api.model.Moto;
import com.smartmotozone.api.smartmotozone_api.service.MotoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/motos")
public class MotoController {

    private final MotoService motoService;

    public MotoController(MotoService motoService) {
        this.motoService = motoService;
    }

    @GetMapping
    public Page<Moto> listar(@PageableDefault Pageable pageable) {
        return motoService.listarTodos(pageable);
    }

    @GetMapping("/buscar")
    public Page<Moto> buscarPorModelo(@RequestParam String modelo, @PageableDefault Pageable pageable) {
        return motoService.buscarPorModelo(modelo, pageable);
    }

    @GetMapping("/zona/{codigoZona}")
    public ResponseEntity<?> buscarPorZona(@PathVariable String codigoZona) {
        var motos = motoService.buscarPorZonaCodigo(codigoZona);
        if (motos.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Nenhuma moto encontrada para a zona " + codigoZona);
        }
        return ResponseEntity.ok(motos);
    }

    @PostMapping
    public ResponseEntity<Moto> criar(@RequestBody @Valid MotoDTO dto) {
        Moto moto = motoService.salvar(dto);
        return ResponseEntity.status(CREATED).body(moto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Moto> buscarPorId(@PathVariable Long id) {
        Moto moto = motoService.buscarPorId(id);
        return ResponseEntity.ok(moto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Moto> atualizar(@PathVariable Long id, @RequestBody @Valid MotoDTO dto) {
        Moto motoAtualizada = motoService.atualizar(id, dto);
        return ResponseEntity.ok(motoAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        motoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
