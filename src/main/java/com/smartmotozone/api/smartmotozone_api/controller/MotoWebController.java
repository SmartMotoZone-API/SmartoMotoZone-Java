package com.smartmotozone.api.smartmotozone_api.controller;

import com.smartmotozone.api.smartmotozone_api.dto.MotoDTO;
import com.smartmotozone.api.smartmotozone_api.model.Moto;
import com.smartmotozone.api.smartmotozone_api.model.Zona;
import com.smartmotozone.api.smartmotozone_api.service.MotoService;
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

import java.util.List;


@Controller
@RequestMapping("/web/motos")
@PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
public class MotoWebController {

    private final MotoService motoService;
    private final ZonaService zonaService;

    public MotoWebController(MotoService motoService, ZonaService zonaService) {
        this.motoService = motoService;
        this.zonaService = zonaService;
    }

    @GetMapping
    public String listarMotos(Model model, @PageableDefault(size = 10, sort = "modelo") Pageable pageable) {
        Page<Moto> page = motoService.listarTodos(pageable);
        model.addAttribute("page", page);
        return "motos/lista";
    }


    @GetMapping("/novo")
    @PreAuthorize("hasRole('ADMIN')")
    public String formNovaMoto(Model model) {
        model.addAttribute("motoDTO", new MotoDTO(null, "", "", null, ""));
        List<Zona> zonas = zonaService.listarTodos(Pageable.unpaged()).getContent();
        model.addAttribute("zonas", zonas);
        return "motos/form";
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String formEditarMoto(@PathVariable Long id, Model model) {
        Moto moto = motoService.buscarPorId(id);
        MotoDTO motoDTO = new MotoDTO(
                moto.getId(),
                moto.getModelo(),
                moto.getPlaca(),
                moto.getZona().getId(),
                moto.getStatus()
        );
        model.addAttribute("motoDTO", motoDTO);
        List<Zona> zonas = zonaService.listarTodos(Pageable.unpaged()).getContent();
        model.addAttribute("zonas", zonas);

        return "motos/form";
    }


    @PostMapping("/salvar")
    @PreAuthorize("hasRole('ADMIN')")
    public String salvarMoto(@Valid @ModelAttribute("motoDTO") MotoDTO motoDTO,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            List<Zona> zonas = zonaService.listarTodos(Pageable.unpaged()).getContent();
            model.addAttribute("zonas", zonas);
            return "motos/form";
        }

        if (motoDTO.id() != null) {
            motoService.atualizar(motoDTO.id(), motoDTO);
            redirectAttributes.addFlashAttribute("sucesso", "Moto atualizada com sucesso!");
        } else {
            motoService.salvar(motoDTO);
            redirectAttributes.addFlashAttribute("sucesso", "Moto cadastrada com sucesso!");
        }
        return "redirect:/web/motos";
    }

    @GetMapping("/deletar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deletarMoto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            motoService.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Moto deletada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao deletar moto: " + e.getMessage());
        }
        return "redirect:/web/motos";
    }
}