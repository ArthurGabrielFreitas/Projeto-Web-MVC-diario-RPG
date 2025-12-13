package br.com.diario.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Entity
@Table(name = "magia")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Magia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Escola/Tipo é obrigatório")
    private String escola;

    @Lob
    private String descricao;

    @Positive
    private Integer circulo = 1;

    private String custo;

    @ManyToMany(mappedBy = "magias")
    @Builder.Default
    private Set<Personagem> personagens = new HashSet<>();

    @ManyToMany(mappedBy = "magias")
    @Builder.Default
    private Set<Ameaca> ameacas = new HashSet<>();

}

