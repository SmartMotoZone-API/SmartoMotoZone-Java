package com.smartmotozone.api.smartmotozone_api.controller;

import com.smartmotozone.api.smartmotozone_api.dto.UsuarioDTO;
import com.smartmotozone.api.smartmotozone_api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web")
public class RegistrationWebController {

    private final UsuarioService usuarioService;

    public RegistrationWebController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioDTO(null, "", "USUARIO", "", "", ""));
        return "register";
    }


    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("usuarioDTO") UsuarioDTO dto,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        try {

            UsuarioDTO dtoSeguro = new UsuarioDTO(
                    null,
                    dto.nome(),
                    "USUARIO",
                    dto.login(),
                    dto.senha(),
                    dto.email()
            );

            usuarioService.salvar(dtoSeguro);

            redirectAttributes.addFlashAttribute("sucesso", "Conta criada! Faça o login.");
            return "redirect:/web/login";

        } catch (Exception e) {
            bindingResult.rejectValue("login", "error.user", "Login ou Email já cadastrado. Tente outro.");
            return "register";
        }
    }
}