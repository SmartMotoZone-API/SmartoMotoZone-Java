package com.smartmotozone.api.smartmotozone_api.controller;

import com.smartmotozone.api.smartmotozone_api.dto.MovimentacaoDTO;
import com.smartmotozone.api.smartmotozone_api.model.Movimentacao;
import com.smartmotozone.api.smartmotozone_api.service.MotoService;
import com.smartmotozone.api.smartmotozone_api.service.MovimentacaoService;
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

import java.time.LocalDateTime;

@Controller
@RequestMapping("/web/movimentacoes")
@PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
public class MovimentacaoWebController {

    private final MovimentacaoService movimentacaoService;
    private final MotoService motoService;
    private final ZonaService zonaService;

    public MovimentacaoWebController(MovimentacaoService movimentacaoService, MotoService motoService, ZonaService zonaService) {
        this.movimentacaoService = movimentacaoService;
        this.motoService = motoService;
        this.zonaService = zonaService;
    }

    @GetMapping
    public String listarMovimentacoes(Model model, @PageableDefault(size = 10, sort = "dataHora") Pageable pageable) {
        Page<Movimentacao> page = movimentacaoService.listarTodas(pageable);
        model.addAttribute("page", page);
        return "movimentacoes/lista";
    }

    @GetMapping("/novo")
    @PreAuthorize("hasRole('ADMIN')")
    public String formNovaMovimentacao(Model model) {
        model.addAttribute("movimentacaoDTO",
                new MovimentacaoDTO(null, null, null, null, "", LocalDateTime.now()));

        model.addAttribute("motos", motoService.listarTodos(Pageable.unpaged()).getContent());
        model.addAttribute("zonas", zonaService.listarTodos(Pageable.unpaged()).getContent());

        return "movimentacoes/form";
    }

    @PostMapping("/salvar")
    @PreAuthorize("hasRole('ADMIN')")
    public String salvarMovimentacao(@Valid @ModelAttribute("movimentacaoDTO") MovimentacaoDTO dto,
                                     BindingResult bindingResult,
                                     Model model,
                                     RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("motos", motoService.listarTodos(Pageable.unpaged()).getContent());
            model.addAttribute("zonas", zonaService.listarTodos(Pageable.unpaged()).getContent());
            return "movimentacoes/form";
        }

        movimentacaoService.salvar(dto);
        redirectAttributes.addFlashAttribute("sucesso", "Movimentação registrada com sucesso!");

        return "redirect:/web/movimentacoes";
    }
}