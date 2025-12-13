package br.com.diario.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "poder")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Poder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Origem é obrigatório")
    private String origem;

    private String custo;

    private String acao;

    @Lob
    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    private String preRequisitos;

    @ManyToMany(mappedBy = "poderes")
    @Builder.Default
    private Set<Personagem> personagens = new HashSet<>();

    @ManyToMany(mappedBy = "poderes")
    @Builder.Default
    private Set<Ameaca> ameacas = new HashSet<>();

}
