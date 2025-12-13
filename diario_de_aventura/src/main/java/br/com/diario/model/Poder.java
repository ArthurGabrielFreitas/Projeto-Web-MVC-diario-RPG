package br.com.diario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
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

    @PositiveOrZero
    private Integer custo = 1;

    private String acao;

    @Lob
    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    private String preRequisitos;

    /**
     * Observação: Poderes podem ter níveis, usos por dia, etc.
     * Campos adicionais podem ser adicionados conforme necessário.
     */
}
