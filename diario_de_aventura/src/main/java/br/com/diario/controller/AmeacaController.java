package br.com.diario.controller;

import br.com.diario.model.Ameaca;
import br.com.diario.service.AmeacaService;
import br.com.diario.service.MagiaService;
import br.com.diario.service.PoderService;
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

    private final AmeacaService ameacaService;
    private final PoderService poderService;
    private final MagiaService magiaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ameacas", ameacaService.listar());
        return "ameaca/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("ameaca", new Ameaca());
        model.addAttribute("poderes", poderService.listar());
        model.addAttribute("magias", magiaService.listar());
        carregarListas(model);
        return "ameaca/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("ameaca") Ameaca ameaca,
            BindingResult result, @RequestParam(required = false) List<Long> poderes,
            @RequestParam(required = false) List<Long> magias) {
        ameacaService.salvar(ameaca, poderes, magias);
        return "redirect:/ameacas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("ameaca", ameacaService.buscar(id));
        carregarListas(model);
        return "ameaca/form";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        ameacaService.deletar(id);
        return "redirect:/ameacas";
    }

    private void carregarListas(Model model) {
        model.addAttribute("poderes", poderService.listar());
        model.addAttribute("magias", magiaService.listar());
    }
}
