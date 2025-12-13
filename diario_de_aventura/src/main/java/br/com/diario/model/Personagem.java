package br.com.diario.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "personagem")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Personagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotBlank(message = "A raça é obrigatória.")
    private String raca;

    @NotBlank(message = "A classe é obrigatória.")
    private String classe;

    @Positive
    private Integer nivel = 1;

    @NotBlank(message = "O jogador é obrigatório.")
    private String jogador;

    @ManyToMany
    @JoinTable(
        name = "personagem_poder",
        joinColumns = @JoinColumn(name = "personagem_id"),
        inverseJoinColumns = @JoinColumn(name = "poder_id")
    )
    @Builder.Default
    private Set<Poder> poderes = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "personagem_magia",
            joinColumns = @JoinColumn(name = "personagem_id"),
            inverseJoinColumns = @JoinColumn(name = "magia_id")
    )
    @Builder.Default
    private Set<Magia> magias = new HashSet<>();

}
