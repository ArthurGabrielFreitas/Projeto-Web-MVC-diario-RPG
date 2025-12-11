package br.com.diario.service;

import br.com.diario.model.Ameaca;
import br.com.diario.repository.AmeacaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AmeacaService {

    private final AmeacaRepository repository;

    public List<Ameaca> listar() {
        return repository.findAll();
    }

    public Ameaca salvar(Ameaca ameaca) {
        return repository.save(ameaca);
    }

    public Ameaca buscar(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
