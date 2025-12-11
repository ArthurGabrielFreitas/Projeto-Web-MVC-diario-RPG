package br.com.diario.service;

import br.com.diario.model.Magia;
import br.com.diario.repository.MagiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MagiaService {

    private final MagiaRepository repository;

    public List<Magia> listar() {
        return repository.findAll();
    }

    public Magia salvar(Magia magia) {
        return repository.save(magia);
    }

    public Magia buscar(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
