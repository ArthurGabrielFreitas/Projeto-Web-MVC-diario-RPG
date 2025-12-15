package br.com.diario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.diario.model.Sessao;
import br.com.diario.repository.SessaoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessaoService {

    private final SessaoRepository repository;

    public List<Sessao> listar() {
        return repository.findAll();
    }

    public Sessao buscarPorId(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public Sessao salvar(Sessao sessao) {
        return repository.save(sessao);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
