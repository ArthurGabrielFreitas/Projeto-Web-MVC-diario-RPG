package br.com.diario.service;

import br.com.diario.model.Relatorio;
import br.com.diario.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final PersonagemRepository personagemRepository;
    private final MagiaRepository magiaRepository;
    private final PoderRepository poderRepository;
    private final SessaoRepository sessaoRepository;
    private final EncontroRepository encontroRepository;
    private final AmeacaRepository ameacaRepository;
    private final ParticipacaoEncontroRepository participacaoEncontroRepository;

    public Relatorio gerarRelatorio() {
        return Relatorio.builder()
                .totalPersonagens(personagemRepository.count())
                .totalMagias(magiaRepository.count())
                .totalPoderes(poderRepository.count())
                .totalSessoes(sessaoRepository.count())
                .totalEncontros(encontroRepository.count())
                .totalAmeacas(ameacaRepository.count())
                .totalParticipacoes(participacaoEncontroRepository.count())
                .build();
    }

}
