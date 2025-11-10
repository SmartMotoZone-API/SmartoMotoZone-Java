package com.smartmotozone.api.smartmotozone_api.controller;

import com.smartmotozone.api.smartmotozone_api.dto.ZonaDTO;
import com.smartmotozone.api.smartmotozone_api.model.Zona;
import com.smartmotozone.api.smartmotozone_api.service.ZonaService;
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
@RequestMapping("/web/zonas")
@PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
public class ZonaWebController {

    private final ZonaService zonaService;

    public ZonaWebController(ZonaService zonaService) {
        this.zonaService = zonaService;
    }

    @GetMapping
    public String listarZonas(Model model, @PageableDefault(size = 10, sort = "descricao") Pageable pageable) {
        Page<Zona> page = zonaService.listarTodos(pageable);
        model.addAttribute("page", page);
        return "zonas/lista";
    }

    @GetMapping("/novo")
    @PreAuthorize("hasRole('ADMIN')")
    public String formNovaZona(Model model) {
        model.addAttribute("zonaDTO", new ZonaDTO(null, "", ""));
        return "zonas/form";
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String formEditarZona(@PathVariable Long id, Model model) {
        Zona zona = zonaService.buscarPorId(id);
        ZonaDTO zonaDTO = new ZonaDTO(zona.getId(), zona.getCodigo(), zona.getDescricao());
        model.addAttribute("zonaDTO", zonaDTO);
        return "zonas/form";
    }

    @PostMapping("/salvar")
    @PreAuthorize("hasRole('ADMIN')")
    public String salvarZona(@Valid @ModelAttribute("zonaDTO") ZonaDTO zonaDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "zonas/form";
        }
        if (zonaDTO.id() != null) {
            zonaService.atualizar(zonaDTO.id(), zonaDTO);
            redirectAttributes.addFlashAttribute("sucesso", "Zona atualizada com sucesso!");
        } else {
            zonaService.salvar(zonaDTO);
            redirectAttributes.addFlashAttribute("sucesso", "Zona cadastrada com sucesso!");
        }

        return "redirect:/web/zonas";
    }

    @GetMapping("/deletar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deletarZona(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            zonaService.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Zona deletada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao deletar zona. Verifique se ela não está sendo usada por uma moto.");
        }
        return "redirect:/web/zonas";
    }
}