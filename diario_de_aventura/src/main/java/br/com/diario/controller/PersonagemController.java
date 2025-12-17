package br.com.diario.controller;

import br.com.diario.model.Personagem;
import br.com.diario.service.MagiaService;
import br.com.diario.service.PersonagemService;
import br.com.diario.service.PoderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/personagens")
@RequiredArgsConstructor
public class PersonagemController {

    private final PersonagemService personagemService;
    private final PoderService poderService;
    private final MagiaService magiaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("personagens", personagemService.listar());
        return "personagem/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("personagem", new Personagem());
        model.addAttribute("poderes", poderService.listar());
        model.addAttribute("magias", magiaService.listar());
        carregarListas(model);
        return "personagem/form";
    }

    @PostMapping
    public String salvar(@Valid Personagem personagem, @RequestParam(required = false) List<Long> poderes,
            @RequestParam(required = false) List<Long> magias) {
        personagemService.salvar(personagem, poderes, magias);
        return "redirect:/personagens";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("personagem", personagemService.buscar(id));
        carregarListas(model);
        return "personagem/form";
    }
    

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        personagemService.deletar(id);
        return "redirect:/personagens";
    }

    private void carregarListas(Model model) {
        model.addAttribute("poderes", poderService.listar());
        model.addAttribute("magias", magiaService.listar());
    }
}
