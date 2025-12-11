package br.com.diario.model;

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

    @NotBlank(message = "Categoria é obrigatória")
    private String categoria;

    @Lob
    private String descricao;

    /**
     * Observação: Poderes podem ter níveis, usos por dia, etc.
     * Campos adicionais podem ser adicionados conforme necessário.
     */
}
