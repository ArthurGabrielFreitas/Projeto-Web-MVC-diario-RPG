package br.com.diario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.diario.model.Encontro;
import java.util.Objects;
import java.util.stream.Collectors;
import br.com.diario.repository.EncontroRepository;
import br.com.diario.repository.PersonagemRepository;
import br.com.diario.repository.AmeacaRepository;
import br.com.diario.repository.SessaoRepository;
import br.com.diario.model.Personagem;
import br.com.diario.model.Ameaca;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EncontroService {

    private final EncontroRepository repository;
    private final PersonagemRepository personagemRepository;
    private final AmeacaRepository ameacaRepository;
    private final SessaoRepository sessaoRepository;

    public List<Encontro> listar() {
        return repository.findAll();
    }

    public Encontro buscarPorId(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public Encontro salvar(Encontro encontro) {
        // Resolver sessão a partir do id (se enviado)
        if (encontro.getSessao() != null && encontro.getSessao().getId() != null) {
            encontro.setSessao(sessaoRepository.findById(encontro.getSessao().getId()).orElse(null));
        } else {
            encontro.setSessao(null);
        }

        if (encontro.getParticipacoes() != null) {
            // Filtra apenas participações marcadas para participar (participa == true)
            var participacoesFiltradas = encontro.getParticipacoes().stream()
                    .filter(Objects::nonNull)
                    .filter(p -> Boolean.TRUE.equals(p.getParticipa()))
                    .peek(p -> p.setEncontro(encontro))
                    .collect(Collectors.toList());

            // Para cada participação, resolver personagem/ameaca por id (se presente)
            for (var p : participacoesFiltradas) {
                if (p.getPersonagem() != null && p.getPersonagem().getId() != null) {
                    Personagem pers = personagemRepository.findById(p.getPersonagem().getId()).orElse(null);
                    p.setPersonagem(pers);
                }
                if (p.getAmeaca() != null && p.getAmeaca().getId() != null) {
                    Ameaca am = ameacaRepository.findById(p.getAmeaca().getId()).orElse(null);
                    p.setAmeaca(am);
                }
            }

            encontro.setParticipacoes(participacoesFiltradas);
        }

        return repository.save(encontro);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
