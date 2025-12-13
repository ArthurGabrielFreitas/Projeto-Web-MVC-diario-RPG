package br.com.diario.service;

import br.com.diario.model.Personagem;
import br.com.diario.repository.MagiaRepository;
import br.com.diario.repository.PersonagemRepository;
import br.com.diario.repository.PoderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonagemService {

    private final PersonagemRepository repository;
    private final PoderRepository poderRepository;
    private final MagiaRepository magiaRepository;

    public List<Personagem> listar() {
        return repository.findAll();
    }

    public Personagem salvar(Personagem personagem,
                         List<Long> poderesIds,
                         List<Long> magiasIds) {

    if (poderesIds != null && !poderesIds.isEmpty()) {
        var poderes = poderRepository.findAllById(poderesIds);
        personagem.setPoderes(new HashSet<>(poderes));
    }

    if (magiasIds != null && !magiasIds.isEmpty()) {
        var magias = magiaRepository.findAllById(magiasIds);
        personagem.setMagias(new HashSet<>(magias));
    }

    return repository.save(personagem);
}


    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public Personagem buscar(Long id) {
        return repository.findById(id).orElse(null);
    }
}
