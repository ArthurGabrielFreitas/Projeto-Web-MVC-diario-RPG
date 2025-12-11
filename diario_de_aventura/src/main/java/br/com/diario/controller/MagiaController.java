package br.com.diario.controller;

import br.com.diario.model.Magia;
import br.com.diario.service.MagiaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/magias")
@RequiredArgsConstructor
public class MagiaController {

    private final MagiaService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("magias", service.listar());
        return "magia/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("magia", new Magia());
        return "magia/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("magia") Magia magia,
                         BindingResult result) {
        if (result.hasErrors()) {
            return "magia/form";
        }
        service.salvar(magia);
        return "redirect:/magias";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("magia", service.buscar(id));
        return "magia/form";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        service.deletar(id);
        return "redirect:/magias";
    }
}
