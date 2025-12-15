package br.com.diario.config;

import br.com.diario.model.*;
import br.com.diario.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataLoader {

    private final PersonagemRepository personagemRepository;
    private final PoderRepository poderRepository;
    private final MagiaRepository magiaRepository;
    private final AmeacaRepository ameacaRepository;
    private final SessaoRepository sessaoRepository;
    private final EncontroRepository encontroRepository;

    @Bean
    CommandLineRunner loadData() {
        return args -> {

            /*
             * =====================
             * PODERES
             * =====================
             */
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

            /*
             * =====================
             * MAGIAS
             * =====================
             */
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

            /*
             * =====================
             * PERSONAGENS
             * =====================
             */
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

            /*
             * =====================
             * AMEAÇAS
             * =====================
             */
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
                    .build();

            ameacaRepository.saveAll(Set.of(goblin, necromante, armadilha));

            /*
             * =======================
             * SESSÃO
             * ========================
             */
            Sessao sessao1 = Sessao.builder()
                    .titulo("Sessão 01 – A Cripta")
                    .data(LocalDate.now())
                    .duracaoHoras(4)
                    .jogadoresPresentes("João, Maria")
                    .build();

            sessaoRepository.save(sessao1);

            /*
             * =======================
             * ENCONTRO
             * ========================
             */
            Encontro encontro = new Encontro();
            encontro.setDescricao("Combate contra goblins na entrada da cripta.");
            encontro.setDuracaoTurnos(6);
            encontro.setSessao(sessao1);

            /*
             * =======================
             * PARTICIPAÇÕES
             * ========================
             */

            // Personagem - Guerreiro
            ParticipacaoEncontro p1 = new ParticipacaoEncontro();
            p1.setEncontro(encontro);
            p1.setPersonagem(guerreiro);
            p1.setMorte(false);
            p1.setUltimoGolpe(true);
            p1.setAnotacoes("Derrubou o líder goblin.");

            // Personagem - Mago
            ParticipacaoEncontro p2 = new ParticipacaoEncontro();
            p2.setEncontro(encontro);
            p2.setPersonagem(mago);
            p2.setMorte(false);
            p2.setUltimoGolpe(false);
            p2.setAnotacoes("Usou bola de fogo para controlar o campo.");

            // Ameaça - Goblin
            ParticipacaoEncontro p3 = new ParticipacaoEncontro();
            p3.setEncontro(encontro);
            p3.setAmeaca(goblin);
            p3.setMorte(true);
            p3.setUltimoGolpe(false);
            p3.setAnotacoes("Derrotado no início do combate.");

            encontro.getParticipacoes().add(p1);
            encontro.getParticipacoes().add(p2);
            encontro.getParticipacoes().add(p3);

            // SALVA SOMENTE O ENCONTRO
            encontroRepository.save(encontro);
        };
    }
}
