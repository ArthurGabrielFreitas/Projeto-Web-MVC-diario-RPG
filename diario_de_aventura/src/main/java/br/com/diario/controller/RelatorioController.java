package br.com.diario.controller;

import br.com.diario.service.RelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/relatorio")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioService relatorioService;

    @GetMapping
    public String relatorio(Model model) {
        model.addAttribute("relatorio", relatorioService.gerarRelatorio());
        // usa o template existente em templates/home/dados.html
        return "home/dados";
    }

}
