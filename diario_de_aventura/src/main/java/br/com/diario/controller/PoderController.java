package br.com.diario.controller;

import br.com.diario.model.Poder;
import br.com.diario.service.PoderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/poderes")
@RequiredArgsConstructor
public class PoderController {

    private final PoderService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("poderes", service.listar());
        return "poder/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("poder", new Poder());
        return "poder/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("poder") Poder poder, BindingResult result) {
        if (result.hasErrors()) {
            return "poder/form";
        }
        service.salvar(poder);
        return "redirect:/poderes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("poder", service.buscar(id));
        return "poder/form";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        service.deletar(id);
        return "redirect:/poderes";
    }
}
