package br.com.diario.model;

import java.util.HashSet;
import java.util.Set;

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
    @JoinColumn(name = "sessao_id")
    private Sessao sessao;

    @OneToMany(mappedBy = "encontro", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ParticipacaoEncontro> participacoes = new HashSet<>();
}
