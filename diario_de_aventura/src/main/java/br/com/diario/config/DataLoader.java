package br.com.diario.config;

import br.com.diario.model.*;
import br.com.diario.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataLoader {

    private final PersonagemRepository personagemRepository;
    private final PoderRepository poderRepository;
    private final MagiaRepository magiaRepository;
    private final AmeacaRepository ameacaRepository;

    @Bean
    CommandLineRunner loadData() {
        return args -> {

            /* =====================
               PODERES
               ===================== */
            Poder ataquePoderoso = Poder.builder()
                    .nome("Ataque Poderoso")
                    .origem("Combate")
                    .custo("1")
                    .acao("Padrão")
                    .descricao("Você causa dano adicional em ataques corpo a corpo.")
                    .preRequisitos("Força 13")
                    .build();

            Poder esquiva = Poder.builder()
                    .nome("Esquiva")
                    .origem("Defesa")
                    .custo("1")
                    .acao("Passiva")
                    .descricao("Você recebe bônus de defesa.")
                    .build();

            Poder magiaInata = Poder.builder()
                    .nome("Magia Inata")
                    .origem("Místico")
                    .custo("2")
                    .acao("Livre")
                    .descricao("Você conjura uma magia simples sem custo.")
                    .build();

            poderRepository.saveAll(Set.of(ataquePoderoso, esquiva, magiaInata));

            /* =====================
               MAGIAS
               ===================== */
            Magia bolaDeFogo = Magia.builder()
                    .nome("Bola de Fogo")
                    .escola("Evocação")
                    .circulo(3)
                    .custo("3 PM")
                    .descricao("Uma explosão de fogo que causa dano em área.")
                    .build();

            Magia curarFerimentos = Magia.builder()
                    .nome("Curar Ferimentos")
                    .escola("Conjuração")
                    .circulo(1)
                    .custo("1 PM")
                    .descricao("Restaura pontos de vida.")
                    .build();

            Magia invisibilidade = Magia.builder()
                    .nome("Invisibilidade")
                    .escola("Ilusão")
                    .circulo(2)
                    .custo("2 PM")
                    .descricao("O alvo torna-se invisível por um curto período.")
                    .build();

            magiaRepository.saveAll(Set.of(bolaDeFogo, curarFerimentos, invisibilidade));

            /* =====================
               PERSONAGENS
               ===================== */
            Personagem guerreiro = Personagem.builder()
                    .nome("Thoran")
                    .raca("Humano")
                    .classe("Guerreiro")
                    .nivel(3)
                    .jogador("Arthur")
                    .poderes(Set.of(ataquePoderoso, esquiva))
                    .build();

            Personagem mago = Personagem.builder()
                    .nome("Eldrin")
                    .raca("Elfo")
                    .classe("Mago")
                    .nivel(4)
                    .jogador("Arthur")
                    .poderes(Set.of(magiaInata))
                    .magias(Set.of(bolaDeFogo, invisibilidade))
                    .build();

            personagemRepository.saveAll(Set.of(guerreiro, mago));

            /* =====================
               AMEAÇAS
               ===================== */
            Ameaca goblin = Ameaca.builder()
                    .nome("Goblin Selvagem")
                    .tipo("CRIATURA")
                    .nivelDificuldade(1)
                    .descricao("Criatura pequena e agressiva.")
                    .poderes(Set.of(ataquePoderoso))
                    .build();

            Ameaca necromante = Ameaca.builder()
                    .nome("Necromante Profano")
                    .tipo("CRIATURA")
                    .nivelDificuldade(5)
                    .descricao("Usuário de magias sombrias.")
                    .poderes(Set.of(magiaInata))
                    .magias(Set.of(bolaDeFogo, curarFerimentos))
                    .build();

            Ameaca armadilha = Ameaca.builder()
                    .nome("Armadilha de Lâminas")
                    .tipo("PERIGO_COMPLEXO")
                    .nivelDificuldade(3)
                    .descricao("Dispositivo mecânico mortal.")
                    .build(); // sem poderes ou magias

            ameacaRepository.saveAll(Set.of(goblin, necromante, armadilha));
        };
    }
}

