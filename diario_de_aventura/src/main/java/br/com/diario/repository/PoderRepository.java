package br.com.diario.repository;

import br.com.diario.model.Poder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoderRepository extends JpaRepository<Poder, Long> {
}
