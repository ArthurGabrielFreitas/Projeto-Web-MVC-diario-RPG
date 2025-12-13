package br.com.diario.controller;

import br.com.diario.model.Ameaca;
import br.com.diario.service.AmeacaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ameacas")
@RequiredArgsConstructor
public class AmeacaController {

    private final AmeacaService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ameacas", service.listar());
        return "ameaca/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("ameaca", new Ameaca());
        return "ameaca/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("ameaca") Ameaca ameaca,
            BindingResult result, @RequestParam(required = false) List<Long> poderes,
            @RequestParam(required = false) List<Long> magias) {
        service.salvar(ameaca, poderes, magias);
        return "redirect:/ameacas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Ameaca a = service.buscar(id);
        model.addAttribute("ameaca", a);
        return "ameaca/form";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        service.deletar(id);
        return "redirect:/ameacas";
    }
}
