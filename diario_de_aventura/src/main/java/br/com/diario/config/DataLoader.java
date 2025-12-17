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

            /*
             * =====================
             * MAIS DADOS DE TESTE
             * =====================
             */

            // Novos personagens
            Personagem ladino = Personagem.builder()
                    .nome("Kara")
                    .raca("Hynne")
                    .classe("Ladino")
                    .nivel(2)
                    .jogador("Marcos")
                    .poderes(Set.of(esquiva))
                    .build();

            Personagem clero = Personagem.builder()
                    .nome("Bran")
                    .raca("Anão")
                    .classe("Clérigo")
                    .nivel(3)
                    .jogador("Paula")
                    .poderes(Set.of(magiaInata))
                    .magias(Set.of(curarFerimentos))
                    .build();

            personagemRepository.saveAll(Set.of(ladino, clero));

            // Novas ameaças
            Ameaca orc = Ameaca.builder()
                    .nome("Orc Berserker")
                    .tipo("CRIATURA")
                    .nivelDificuldade(4)
                    .descricao("Orc raivoso e forte.")
                    .poderes(Set.of(ataquePoderoso))
                    .build();

            Ameaca espectro = Ameaca.builder()
                    .nome("Espectro Errante")
                    .tipo("ESPIRITO")
                    .nivelDificuldade(6)
                    .descricao("Entidade incorpórea que persegue os vivos.")
                    .magias(Set.of(invisibilidade))
                    .build();

            ameacaRepository.saveAll(Set.of(orc, espectro));

            // Nova sessão
            Sessao sessao2 = Sessao.builder()
                    .titulo("Sessão 02 – Ruínas Antigas")
                    .data(LocalDate.now().plusDays(7))
                    .duracaoHoras(3)
                    .jogadoresPresentes("João, Maria, Marcos")
                    .build();

            sessaoRepository.save(sessao2);

            // Encontro adicional 1 (na sessão 2)
            Encontro encontro2 = new Encontro();
            encontro2.setDescricao("Emboscada na trilha entre vilarejos.");
            encontro2.setDuracaoTurnos(4);
            encontro2.setSessao(sessao2);

            ParticipacaoEncontro e2p1 = new ParticipacaoEncontro();
            e2p1.setEncontro(encontro2);
            e2p1.setPersonagem(ladino);
            e2p1.setMorte(false);
            e2p1.setUltimoGolpe(false);
            e2p1.setAnotacoes("Se esquivou e atacou pelas sombras.");

            ParticipacaoEncontro e2p2 = new ParticipacaoEncontro();
            e2p2.setEncontro(encontro2);
            e2p2.setAmeaca(orc);
            e2p2.setMorte(true);
            e2p2.setUltimoGolpe(true);
            e2p2.setAnotacoes("Orc derrotado após resistência.");

            encontro2.getParticipacoes().add(e2p1);
            encontro2.getParticipacoes().add(e2p2);

            encontroRepository.save(encontro2);

            // Encontro adicional 2 (na sessão 1)
            Encontro encontro3 = new Encontro();
            encontro3.setDescricao("Investigação nas catacumbas - espectro observado.");
            encontro3.setDuracaoTurnos(3);
            encontro3.setSessao(sessao1);

            ParticipacaoEncontro e3p1 = new ParticipacaoEncontro();
            e3p1.setEncontro(encontro3);
            e3p1.setPersonagem(clero);
            e3p1.setMorte(false);
            e3p1.setUltimoGolpe(false);
            e3p1.setAnotacoes("Curou o grupo durante o encontro.");

            ParticipacaoEncontro e3p2 = new ParticipacaoEncontro();
            e3p2.setEncontro(encontro3);
            e3p2.setAmeaca(espectro);
            e3p2.setMorte(false);
            e3p2.setUltimoGolpe(false);
            e3p2.setAnotacoes("Espectro fugiu após luz sagrada.");

            encontro3.getParticipacoes().add(e3p1);
            encontro3.getParticipacoes().add(e3p2);

            encontroRepository.save(encontro3);

            /*
             * =====================
             * ENTRADAS ADICIONAIS PARA TESTE (RAÇAS/CLASSES/ULTIMO GOLPE)
             * =====================
             */

            Personagem extra1 = Personagem.builder()
                    .nome("Rurik")
                    .raca("Humano")
                    .classe("Guerreiro")
                    .nivel(2)
                    .jogador("Lucas")
                    .poderes(Set.of(ataquePoderoso))
                    .build();

            Personagem extra2 = Personagem.builder()
                    .nome("Sylra")
                    .raca("Elfo")
                    .classe("Ladino")
                    .nivel(3)
                    .jogador("Mariana")
                    .poderes(Set.of(esquiva))
                    .build();

            Personagem extra3 = Personagem.builder()
                    .nome("Thalia")
                    .raca("Humano")
                    .classe("Clérigo")
                    .nivel(4)
                    .jogador("Pedro")
                    .magias(Set.of(curarFerimentos))
                    .build();

            Personagem extra4 = Personagem.builder()
                    .nome("Grom")
                    .raca("Orc")
                    .classe("Bárbaro")
                    .nivel(5)
                    .jogador("Ana")
                    .poderes(Set.of(ataquePoderoso))
                    .build();

            personagemRepository.saveAll(Set.of(extra1, extra2, extra3, extra4));

            // Encontro extra 4
            Encontro encontro4 = new Encontro();
            encontro4.setDescricao("Confronto noturno na ponte");
            encontro4.setDuracaoTurnos(5);
            encontro4.setSessao(sessao2);

            ParticipacaoEncontro e4p1 = new ParticipacaoEncontro();
            e4p1.setEncontro(encontro4);
            e4p1.setPersonagem(extra1);
            e4p1.setMorte(false);
            e4p1.setUltimoGolpe(true);
            e4p1.setAnotacoes("Desferiu o golpe final sobre o saqueador.");

            ParticipacaoEncontro e4p2 = new ParticipacaoEncontro();
            e4p2.setEncontro(encontro4);
            e4p2.setAmeaca(orc);
            e4p2.setMorte(true);
            e4p2.setUltimoGolpe(false);
            e4p2.setAnotacoes("Orc caiu na ponte.");

            encontro4.getParticipacoes().add(e4p1);
            encontro4.getParticipacoes().add(e4p2);
            encontroRepository.save(encontro4);

            // Encontro extra 5
            Encontro encontro5 = new Encontro();
            encontro5.setDescricao("Emboscada na caverna pequena");
            encontro5.setDuracaoTurnos(3);
            encontro5.setSessao(sessao2);

            ParticipacaoEncontro e5p1 = new ParticipacaoEncontro();
            e5p1.setEncontro(encontro5);
            e5p1.setPersonagem(extra2);
            e5p1.setMorte(false);
            e5p1.setUltimoGolpe(true);
            e5p1.setAnotacoes("Ataque surpresa e último golpe marcado.");

            ParticipacaoEncontro e5p2 = new ParticipacaoEncontro();
            e5p2.setEncontro(encontro5);
            e5p2.setAmeaca(goblin);
            e5p2.setMorte(true);
            e5p2.setUltimoGolpe(false);
            e5p2.setAnotacoes("Goblins derrotados.");

            encontro5.getParticipacoes().add(e5p1);
            encontro5.getParticipacoes().add(e5p2);
            encontroRepository.save(encontro5);

            // Encontro extra 6
            Encontro encontro6 = new Encontro();
            encontro6.setDescricao("Retaliação na aldeia");
            encontro6.setDuracaoTurnos(6);
            encontro6.setSessao(sessao1);

            ParticipacaoEncontro e6p1 = new ParticipacaoEncontro();
            e6p1.setEncontro(encontro6);
            e6p1.setPersonagem(extra3);
            e6p1.setMorte(false);
            e6p1.setUltimoGolpe(true);
            e6p1.setAnotacoes("Usou bênção e recebeu último golpe.");

            ParticipacaoEncontro e6p2 = new ParticipacaoEncontro();
            e6p2.setEncontro(encontro6);
            e6p2.setAmeaca(necromante);
            e6p2.setMorte(true);
            e6p2.setUltimoGolpe(false);
            e6p2.setAnotacoes("Necromante derrotado após expulsão.");

            encontro6.getParticipacoes().add(e6p1);
            encontro6.getParticipacoes().add(e6p2);
            encontroRepository.save(encontro6);
        };
    }
}
