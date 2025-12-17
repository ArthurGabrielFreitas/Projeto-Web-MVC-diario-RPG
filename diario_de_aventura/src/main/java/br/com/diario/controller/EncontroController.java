package br.com.diario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
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
        // fornecer mapas vazios para o template evitar indexação em null
        model.addAttribute("mapParticipacaoPorPersonagem", new java.util.HashMap<Long, br.com.diario.model.ParticipacaoEncontro>());
        model.addAttribute("mapParticipacaoPorAmeaca", new java.util.HashMap<Long, br.com.diario.model.ParticipacaoEncontro>());
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

    // Marcar como 'participa' as participações existentes para que o template
    // exiba os checkboxes pré-marcados (edição). Não persistimos isso aqui.
    mapPorPersonagem.values().forEach(part -> part.setParticipa(true));
    mapPorAmeaca.values().forEach(part -> part.setParticipa(true));

        // For id-based binding, fornecemos apenas os mapas para a view utilizar
        model.addAttribute("encontro", encontro);
        model.addAttribute("sessoes", sessaoService.listar());
        model.addAttribute("personagens", personagens);
        model.addAttribute("ameacas", ameacas);
        model.addAttribute("mapParticipacaoPorPersonagem", mapPorPersonagem);
        model.addAttribute("mapParticipacaoPorAmeaca", mapPorAmeaca);

        return "encontro/form";
    }


    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Encontro encontro,
                         @RequestParam(required = false, name = "personagensSelecionados") java.util.List<Long> personagensSelecionados,
                         @RequestParam(required = false, name = "ameacasSelecionadas") java.util.List<Long> ameacasSelecionadas,
                         HttpServletRequest request) {

        var participacoes = new java.util.ArrayList<br.com.diario.model.ParticipacaoEncontro>();

        // Construir participações de personagens a partir dos ids enviados
        if (personagensSelecionados != null) {
            for (Long pid : personagensSelecionados) {
                var part = new br.com.diario.model.ParticipacaoEncontro();
                // se houver um id de participacao existente, mantenha-o para atualização
                String existing = request.getParameter("participacaoId_personagem_" + pid);
                if (existing != null && !existing.isBlank()) {
                    try {
                        part.setId(Long.valueOf(existing));
                    } catch (NumberFormatException e) {
                        // ignore, será tratado como nova participação
                    }
                }
                var pers = new br.com.diario.model.Personagem();
                pers.setId(pid);
                part.setPersonagem(pers);
                part.setParticipa(true);
                part.setMorte(request.getParameter("morte_personagem_" + pid) != null);
                part.setUltimoGolpe(request.getParameter("ultimoGolpe_personagem_" + pid) != null);
                part.setAnotacoes(request.getParameter("anotacoes_personagem_" + pid));
                part.setEncontro(encontro);
                participacoes.add(part);
            }
        }

        // Construir participações de ameaças a partir dos ids enviados
        if (ameacasSelecionadas != null) {
            for (Long aid : ameacasSelecionadas) {
                var part = new br.com.diario.model.ParticipacaoEncontro();
                String existing = request.getParameter("participacaoId_ameaca_" + aid);
                if (existing != null && !existing.isBlank()) {
                    try {
                        part.setId(Long.valueOf(existing));
                    } catch (NumberFormatException e) {
                        // ignore
                    }
                }
                var am = new br.com.diario.model.Ameaca();
                am.setId(aid);
                part.setAmeaca(am);
                part.setParticipa(true);
                part.setMorte(request.getParameter("morte_ameaca_" + aid) != null);
                part.setUltimoGolpe(request.getParameter("ultimoGolpe_ameaca_" + aid) != null);
                part.setAnotacoes(request.getParameter("anotacoes_ameaca_" + aid));
                part.setEncontro(encontro);
                participacoes.add(part);
            }
        }

        encontro.setParticipacoes(participacoes);

        service.salvar(encontro);
        return "redirect:/encontros";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        service.deletar(id);
        return "redirect:/encontros";
    }
}
