package br.com.diario.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Entity
@Table(name = "ameaca")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Ameaca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    /**
     * Tipo: CREATURE (criatura) | HAZARD (perigo complexo) | OUTRO
     */
    @NotBlank(message = "Tipo é obrigatório")
    private String tipo;

    @Lob
    private String descricao;

    @PositiveOrZero
    private Integer nivel = 0;

    @ManyToMany
    @JoinTable(
        name = "ameaca_poder",
        joinColumns = @JoinColumn(name = "ameaca_id"),
        inverseJoinColumns = @JoinColumn(name = "poder_id")
    )
    @Builder.Default
    private Set<Poder> poderes = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "ameaca_magia",
        joinColumns = @JoinColumn(name = "ameaca_id"),
        inverseJoinColumns = @JoinColumn(name = "magia_id")
    )
    @Builder.Default
    private Set<Magia> magias = new HashSet<>();

}
