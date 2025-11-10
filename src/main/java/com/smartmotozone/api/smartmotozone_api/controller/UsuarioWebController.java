package com.smartmotozone.api.smartmotozone_api.controller;

import com.smartmotozone.api.smartmotozone_api.dto.UsuarioDTO;
import com.smartmotozone.api.smartmotozone_api.enums.UserRole;
import com.smartmotozone.api.smartmotozone_api.model.Usuario;
import com.smartmotozone.api.smartmotozone_api.service.UsuarioService;
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
@RequestMapping("/web/usuarios")
@PreAuthorize("hasRole('ADMIN')")
public class UsuarioWebController {

    private final UsuarioService usuarioService;

    public UsuarioWebController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listarUsuarios(Model model, @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<Usuario> page = usuarioService.listarTodos(pageable);
        model.addAttribute("page", page);
        return "usuarios/lista";
    }

    @GetMapping("/novo")
    public String formNovoUsuario(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioDTO(null, "", "USUARIO", "", "", ""));
        model.addAttribute("perfis", UserRole.values());
        return "usuarios/form";
    }

    @GetMapping("/editar/{id}")
    public String formEditarUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id);
        UsuarioDTO dto = new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getPerfil().name(), usuario.getLogin(), null, usuario.getEmail());
        model.addAttribute("usuarioDTO", dto);
        model.addAttribute("perfis", UserRole.values());
        return "usuarios/form";
    }

    @PostMapping("/salvar")
    public String salvarUsuario(@Valid @ModelAttribute("usuarioDTO") UsuarioDTO dto,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("perfis", UserRole.values());
            return "usuarios/form";
        }

        if (dto.id() != null && (dto.senha() == null || dto.senha().isBlank())) {
            Usuario usuarioExistente = usuarioService.buscarPorId(dto.id());
            UsuarioDTO dtoAtualizado = new UsuarioDTO(
                    dto.id(), dto.nome(), dto.perfil(), dto.login(),
                    usuarioExistente.getPassword(),
                    dto.email()
            );
            usuarioService.atualizar(dto.id(), dtoAtualizado);
            redirectAttributes.addFlashAttribute("sucesso", "Usuário atualizado (senha mantida)!");

        } else {
            if (dto.id() != null) {
                usuarioService.atualizar(dto.id(), dto);
                redirectAttributes.addFlashAttribute("sucesso", "Usuário atualizado (nova senha salva)!");
            } else {
                usuarioService.salvar(dto);
                redirectAttributes.addFlashAttribute("sucesso", "Usuário cadastrado com sucesso!");
            }
        }

        return "redirect:/web/usuarios";
    }

    @GetMapping("/deletar/{id}")
    public String deletarUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Usuário deletado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao deletar usuário.");
        }
        return "redirect:/web/usuarios";
    }
}