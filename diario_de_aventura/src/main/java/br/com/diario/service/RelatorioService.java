package br.com.diario.service;

import br.com.diario.model.Relatorio;
import br.com.diario.model.CountItem;
import br.com.diario.model.ParticipantCount;
import br.com.diario.model.UltimoGolpeCount;
import br.com.diario.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        Relatorio r = Relatorio.builder()
                .totalPersonagens(personagemRepository.count())
                .totalMagias(magiaRepository.count())
                .totalPoderes(poderRepository.count())
                .totalSessoes(sessaoRepository.count())
                .totalEncontros(encontroRepository.count())
                .totalAmeacas(ameacaRepository.count())
                .totalParticipacoes(participacaoEncontroRepository.count())
                .build();

        // Top raças
        List<CountItem> topRacas = new ArrayList<>();
        personagemRepository.countByRaca().forEach(row -> {
            String raca = (String) row[0];
            long cnt = ((Number) row[1]).longValue();
            topRacas.add(new CountItem(raca, cnt));
        });
        r.setTopRacas(topRacas);

        // Top classes
        List<CountItem> topClasses = new ArrayList<>();
        personagemRepository.countByClasse().forEach(row -> {
            String classe = (String) row[0];
            long cnt = ((Number) row[1]).longValue();
            topClasses.add(new CountItem(classe, cnt));
        });
        r.setTopClasses(topClasses);

        // Top participantes (personagens)
        List<ParticipantCount> topParticipantes = new ArrayList<>();
        participacaoEncontroRepository.countParticipacoesPorPersonagem().forEach(row -> {
            Long id = ((Number) row[0]).longValue();
            String nome = (String) row[1];
            long cnt = ((Number) row[2]).longValue();
            topParticipantes.add(new ParticipantCount(id, nome, cnt));
        });
        r.setTopParticipantes(topParticipantes);

        // Top participações por ameaca
        List<ParticipantCount> topAmeacas = new ArrayList<>();
        participacaoEncontroRepository.countParticipacoesPorAmeaca().forEach(row -> {
            Long id = ((Number) row[0]).longValue();
            String nome = (String) row[1];
            long cnt = ((Number) row[2]).longValue();
            topAmeacas.add(new ParticipantCount(id, nome, cnt));
        });
        r.setTopAmeacasParticipacoes(topAmeacas);

        // Top ultimoGolpe (personagem + ameaca)
        List<UltimoGolpeCount> topUltimo = new ArrayList<>();
        participacaoEncontroRepository.countUltimoGolpePorPersonagem().forEach(row -> {
            Long id = ((Number) row[0]).longValue();
            String nome = (String) row[1];
            long cnt = ((Number) row[2]).longValue();
            topUltimo.add(new UltimoGolpeCount("personagem", id, nome, cnt));
        });
        participacaoEncontroRepository.countUltimoGolpePorAmeaca().forEach(row -> {
            Long id = ((Number) row[0]).longValue();
            String nome = (String) row[1];
            long cnt = ((Number) row[2]).longValue();
            topUltimo.add(new UltimoGolpeCount("ameaca", id, nome, cnt));
        });
        r.setTopUltimoGolpe(topUltimo);

        return r;
    }

}
