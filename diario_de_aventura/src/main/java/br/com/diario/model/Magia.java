package br.com.diario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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

    /**
     * Custo em pontos ou outras notas (string para flexibilidade)
     */
    private String custo;
}

