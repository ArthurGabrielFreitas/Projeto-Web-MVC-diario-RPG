package br.com.diario.model;

import lombok.*;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Relatorio {

    private long totalPersonagens;
    private long totalMagias;
    private long totalPoderes;
    private long totalSessoes;
    private long totalEncontros;
    private long totalAmeacas;
    private long totalParticipacoes;

    // Listas adicionais para relatório analítico
    private List<CountItem> topRacas;
    private List<CountItem> topClasses;
    private List<ParticipantCount> topParticipantes; // personagens com mais participações
    private List<ParticipantCount> topAmeacasParticipacoes; // ameaças com mais participações
    private List<UltimoGolpeCount> topUltimoGolpe; // personagens/ameaças com mais ultimoGolpe

}
