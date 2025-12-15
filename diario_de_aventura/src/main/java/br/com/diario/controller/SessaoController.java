package br.com.diario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.diario.model.Sessao;
import br.com.diario.service.SessaoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/sessoes")
@RequiredArgsConstructor
public class SessaoController {

    private final SessaoService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("sessoes", service.listar());
        return "sessao/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("sessao", new Sessao());
        return "sessao/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Sessao sessao) {
        service.salvar(sessao);
        return "redirect:/sessoes";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        service.deletar(id);
        return "redirect:/sessoes";
    }
}

