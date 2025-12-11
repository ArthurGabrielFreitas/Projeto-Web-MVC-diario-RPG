package br.com.diario.model;

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
}
