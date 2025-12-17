package br.com.diario.model;

import lombok.*;

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

}
