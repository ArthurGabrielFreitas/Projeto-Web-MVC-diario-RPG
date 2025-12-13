package br.com.diario.service;

import br.com.diario.model.Ameaca;
import br.com.diario.repository.AmeacaRepository;
import br.com.diario.repository.MagiaRepository;
import br.com.diario.repository.PoderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AmeacaService {

    private final AmeacaRepository repository;
    private final PoderRepository poderRepository;
    private final MagiaRepository magiaRepository;

    public List<Ameaca> listar() {
        return repository.findAll();
    }

    public Ameaca salvar(Ameaca ameaca,
                     List<Long> poderesIds,
                     List<Long> magiasIds) {

    if (poderesIds != null && !poderesIds.isEmpty()) {
        ameaca.setPoderes(
            new HashSet<>(poderRepository.findAllById(poderesIds))
        );
    }

    if (magiasIds != null && !magiasIds.isEmpty()) {
        ameaca.setMagias(
            new HashSet<>(magiaRepository.findAllById(magiasIds))
        );
    }

    return repository.save(ameaca);
}


    public Ameaca buscar(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
