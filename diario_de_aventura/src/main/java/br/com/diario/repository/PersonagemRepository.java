package br.com.diario.repository;

import br.com.diario.model.Personagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonagemRepository extends JpaRepository<Personagem, Long> {
}