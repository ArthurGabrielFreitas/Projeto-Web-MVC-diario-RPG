package br.com.diario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.diario.model.Encontro;
import br.com.diario.repository.EncontroRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EncontroService {

    private final EncontroRepository repository;

    public List<Encontro> listar() {
        return repository.findAll();
    }

    public Encontro buscarPorId(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public Encontro salvar(Encontro encontro) {
        if (encontro.getParticipacoes() != null) {
            encontro.getParticipacoes().forEach(p -> {
                p.setEncontro(encontro);
            });
        }
        return repository.save(encontro);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
