package br.com.diario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

import br.com.diario.model.ParticipacaoEncontro;

public interface ParticipacaoEncontroRepository extends JpaRepository<ParticipacaoEncontro, Long> {

	@Query("SELECT p.personagem.id, p.personagem.nome, COUNT(p) FROM ParticipacaoEncontro p WHERE p.personagem IS NOT NULL GROUP BY p.personagem.id, p.personagem.nome ORDER BY COUNT(p) DESC")
	List<Object[]> countParticipacoesPorPersonagem();

	@Query("SELECT p.ameaca.id, p.ameaca.nome, COUNT(p) FROM ParticipacaoEncontro p WHERE p.ameaca IS NOT NULL GROUP BY p.ameaca.id, p.ameaca.nome ORDER BY COUNT(p) DESC")
	List<Object[]> countParticipacoesPorAmeaca();

	@Query("SELECT p.personagem.id, p.personagem.nome, COUNT(p) FROM ParticipacaoEncontro p WHERE p.ultimoGolpe = true AND p.personagem IS NOT NULL GROUP BY p.personagem.id, p.personagem.nome ORDER BY COUNT(p) DESC")
	List<Object[]> countUltimoGolpePorPersonagem();

	@Query("SELECT p.ameaca.id, p.ameaca.nome, COUNT(p) FROM ParticipacaoEncontro p WHERE p.ultimoGolpe = true AND p.ameaca IS NOT NULL GROUP BY p.ameaca.id, p.ameaca.nome ORDER BY COUNT(p) DESC")
	List<Object[]> countUltimoGolpePorAmeaca();

}
