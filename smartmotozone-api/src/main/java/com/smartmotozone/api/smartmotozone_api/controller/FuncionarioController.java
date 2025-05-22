package com.smartmotozone.api.smartmotozone_api.controller;

import com.smartmotozone.api.smartmotozone_api.dto.FuncionarioDTO;
import com.smartmotozone.api.smartmotozone_api.model.Funcionario;
import com.smartmotozone.api.smartmotozone_api.service.FuncionarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @PostMapping
    public Funcionario criar(@RequestBody @Valid FuncionarioDTO dto) {
        return funcionarioService.salvar(dto);
    }

    @GetMapping
    public Page<Funcionario> listarTodos(Pageable pageable) {
        return funcionarioService.listarTodos(pageable);
    }

    @GetMapping("/{id}")
    public Funcionario buscarPorId(@PathVariable Long id) {
        return funcionarioService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Funcionario atualizar(@PathVariable Long id, @RequestBody @Valid FuncionarioDTO dto) {
        return funcionarioService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        funcionarioService.deletar(id);
    }
}
