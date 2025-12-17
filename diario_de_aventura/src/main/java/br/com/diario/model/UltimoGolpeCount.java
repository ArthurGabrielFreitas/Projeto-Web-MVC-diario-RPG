package br.com.diario.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UltimoGolpeCount {
    private String tipo; // "personagem" ou "ameaca"
    private Long id;
    private String nome;
    private long count;
}
