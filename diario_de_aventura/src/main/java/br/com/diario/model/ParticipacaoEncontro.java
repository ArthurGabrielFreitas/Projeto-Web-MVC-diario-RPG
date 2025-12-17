package br.com.diario.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "participacao_encontro")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ParticipacaoEncontro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "encontro_id")
    private Encontro encontro;

    @ManyToOne
    @JoinColumn(name = "personagem_id")
    private Personagem personagem;

    @ManyToOne
    @JoinColumn(name = "ameaca_id")
    private Ameaca ameaca;

    private Boolean morte = false;

    private Boolean ultimoGolpe = false;

    // Indica se esta participação deve ser considerada/salva no encontro
    private Boolean participa = false;

    @Lob
    private String anotacoes;
}

