package br.com.diario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.diario.model.Encontro;
import br.com.diario.service.EncontroService;
import br.com.diario.service.SessaoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/encontros")
@RequiredArgsConstructor
public class EncontroController {

    private final EncontroService service;
    private final SessaoService sessaoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("encontros", service.listar());
        return "encontro/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("encontro", new Encontro());
        model.addAttribute("sessoes", sessaoService.listar());
        return "encontro/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Encontro encontro) {
        service.salvar(encontro);
        return "redirect:/encontros";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        service.deletar(id);
        return "redirect:/encontros";
    }
}
