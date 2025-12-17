package br.com.diario.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "encontro")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Encontro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer duracaoTurnos;

    @Lob
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "sessao_id", nullable = true)
    private Sessao sessao;

    @OneToMany(mappedBy = "encontro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParticipacaoEncontro> participacoes = new ArrayList<>();

    // Retorna o conjunto de ameaças presentes nas participações deste encontro
    public java.util.Set<Ameaca> getAmeacas() {
        return participacoes.stream()
                .map(ParticipacaoEncontro::getAmeaca)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
}
