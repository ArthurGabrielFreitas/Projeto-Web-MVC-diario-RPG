package br.com.diario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.diario.model.Encontro;

public interface EncontroRepository extends JpaRepository<Encontro, Long> {

}
