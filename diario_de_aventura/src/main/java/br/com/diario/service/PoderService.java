package br.com.diario.service;

import br.com.diario.model.Poder;
import br.com.diario.repository.PoderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PoderService {

    private final PoderRepository repository;

    public List<Poder> listar() {
        return repository.findAll();
    }

    public Poder salvar(Poder p) {
        return repository.save(p);
    }

    public Poder buscar(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
