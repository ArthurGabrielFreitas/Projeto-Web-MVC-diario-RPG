package br.com.diario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.diario.model.Encontro;
import br.com.diario.service.AmeacaService;
import br.com.diario.service.EncontroService;
import br.com.diario.service.PersonagemService;
import br.com.diario.service.SessaoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/encontros")
@RequiredArgsConstructor
public class EncontroController {

    private final EncontroService service;
    private final SessaoService sessaoService;
    private final PersonagemService personagemService;
    private final AmeacaService ameacaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("encontros", service.listar());
        return "encontro/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("encontro", new Encontro());
        model.addAttribute("sessoes", sessaoService.listar());
        model.addAttribute("personagens", personagemService.listar());
        model.addAttribute("ameacas", ameacaService.listar());
        return "encontro/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Encontro encontro = service.buscarPorId(id);

        // carregar listas de apoio
        var personagens = personagemService.listar();
        var ameacas = ameacaService.listar();

        // mapear participações existentes por personagem/ameaca id
        var mapPorPersonagem = encontro.getParticipacoes().stream()
                .filter(p -> p.getPersonagem() != null && p.getPersonagem().getId() != null)
                .collect(java.util.stream.Collectors.toMap(p -> p.getPersonagem().getId(), p -> p));

        var mapPorAmeaca = encontro.getParticipacoes().stream()
                .filter(p -> p.getAmeaca() != null && p.getAmeaca().getId() != null)
                .collect(java.util.stream.Collectors.toMap(p -> p.getAmeaca().getId(), p -> p));

        // construir lista ordenada: primeiro personagens, depois ameacas
        var participacoesOrdenadas = new java.util.ArrayList<br.com.diario.model.ParticipacaoEncontro>();

        for (var pers : personagens) {
            if (mapPorPersonagem.containsKey(pers.getId())) {
                var part = mapPorPersonagem.get(pers.getId());
                part.setEncontro(encontro);
                part.setParticipa(true);
                participacoesOrdenadas.add(part);
            } else {
                var part = new br.com.diario.model.ParticipacaoEncontro();
                part.setPersonagem(pers);
                part.setParticipa(false);
                part.setEncontro(encontro);
                participacoesOrdenadas.add(part);
            }
        }

        for (var a : ameacas) {
            if (mapPorAmeaca.containsKey(a.getId())) {
                var part = mapPorAmeaca.get(a.getId());
                part.setEncontro(encontro);
                part.setParticipa(true);
                participacoesOrdenadas.add(part);
            } else {
                var part = new br.com.diario.model.ParticipacaoEncontro();
                part.setAmeaca(a);
                part.setParticipa(false);
                part.setEncontro(encontro);
                participacoesOrdenadas.add(part);
            }
        }

        encontro.setParticipacoes(participacoesOrdenadas);

        model.addAttribute("encontro", encontro);
        model.addAttribute("sessoes", sessaoService.listar());
        model.addAttribute("personagens", personagens);
        model.addAttribute("ameacas", ameacas);

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
