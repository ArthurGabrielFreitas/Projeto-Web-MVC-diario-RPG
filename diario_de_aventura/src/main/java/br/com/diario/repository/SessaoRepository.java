package br.com.diario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.diario.model.Sessao;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {}

