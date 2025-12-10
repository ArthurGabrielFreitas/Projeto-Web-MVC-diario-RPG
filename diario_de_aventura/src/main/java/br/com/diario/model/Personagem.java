package br.com.diario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
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

    @PositiveOrZero
    private Integer nivel;
}
