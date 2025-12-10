package br.com.diarioaventuras.controller;

import br.com.diarioaventuras.entity.Personagem;
import br.com.diarioaventuras.service.PersonagemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/personagens")
@RequiredArgsConstructor
public class PersonagemController {

    private final PersonagemService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("personagens", service.listar());
        return "personagem/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("personagem", new Personagem());
        return "personagem/form";
    }

    @PostMapping
    public String salvar(@Valid Personagem personagem) {
        service.salvar(personagem);
        return "redirect:/personagens";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        service.deletar(id);
        return "redirect:/personagens";
    }
}
