package br.com.diario.repository;

import br.com.diario.model.Personagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonagemRepository extends JpaRepository<Personagem, Long> {

	@Query("SELECT p.raca, COUNT(p) FROM Personagem p GROUP BY p.raca ORDER BY COUNT(p) DESC")
	List<Object[]> countByRaca();

	@Query("SELECT p.classe, COUNT(p) FROM Personagem p GROUP BY p.classe ORDER BY COUNT(p) DESC")
	List<Object[]> countByClasse();

}