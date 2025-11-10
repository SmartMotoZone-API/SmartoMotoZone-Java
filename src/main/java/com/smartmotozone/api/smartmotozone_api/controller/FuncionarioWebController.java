package com.smartmotozone.api.smartmotozone_api.controller;

import com.smartmotozone.api.smartmotozone_api.dto.FuncionarioDTO;
import com.smartmotozone.api.smartmotozone_api.model.Funcionario;
import com.smartmotozone.api.smartmotozone_api.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/funcionarios")
@PreAuthorize("hasRole('ADMIN')")
public class FuncionarioWebController {

    private final FuncionarioService funcionarioService;

    public FuncionarioWebController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping
    public String listarFuncionarios(Model model, @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<Funcionario> page = funcionarioService.listarTodos(pageable);
        model.addAttribute("page", page);
        return "funcionarios/lista";
    }

    @GetMapping("/novo")
    public String formNovoFuncionario(Model model) {
        model.addAttribute("funcionarioDTO", new FuncionarioDTO(null, "", ""));
        return "funcionarios/form";
    }

    @GetMapping("/editar/{id}")
    public String formEditarFuncionario(@PathVariable Long id, Model model) {
        Funcionario funcionario = funcionarioService.buscarPorId(id);
        FuncionarioDTO dto = new FuncionarioDTO(funcionario.getId(), funcionario.getNome(), funcionario.getCargo());
        model.addAttribute("funcionarioDTO", dto);
        return "funcionarios/form";
    }

    @PostMapping("/salvar")
    public String salvarFuncionario(@Valid @ModelAttribute("funcionarioDTO") FuncionarioDTO dto,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "funcionarios/form";
        }

        if (dto.id() != null) {
            funcionarioService.atualizar(dto.id(), dto);
            redirectAttributes.addFlashAttribute("sucesso", "Funcionário atualizado com sucesso!");
        } else {
            funcionarioService.salvar(dto);
            redirectAttributes.addFlashAttribute("sucesso", "Funcionário cadastrado com sucesso!");
        }

        return "redirect:/web/funcionarios";
    }

    @GetMapping("/deletar/{id}")
    public String deletarFuncionario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            funcionarioService.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Funcionário deletado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao deletar funcionário.");
        }
        return "redirect:/web/funcionarios";
    }
}