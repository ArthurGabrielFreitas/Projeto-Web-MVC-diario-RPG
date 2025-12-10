package br.com.diario.service;

import br.com.diario.model.Personagem;
import br.com.diario.repository.PersonagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonagemService {

    private final PersonagemRepository repository;

    public List<Personagem> listar() {
        return repository.findAll();
    }

    public Personagem salvar(Personagem personagem) {
        return repository.save(personagem);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public Personagem buscar(Long id) {
        return repository.findById(id).orElse(null);
    }
}
