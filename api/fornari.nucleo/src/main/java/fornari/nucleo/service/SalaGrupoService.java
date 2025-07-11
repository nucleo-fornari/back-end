package fornari.nucleo.service;

import fornari.nucleo.domain.dto.sala.grupo.SalaGrupoResponseDto;
import fornari.nucleo.domain.entity.SalaGrupo;
import fornari.nucleo.repository.SalaGrupoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaGrupoService {

    private final SalaGrupoRepository salaGrupoRepository;

    public SalaGrupo create(String nome) {
        if (!this.salaGrupoRepository.existsByNome(nome)) return this.salaGrupoRepository.save(new SalaGrupo(nome));
        throw new ResponseStatusException(HttpStatus.CONFLICT);
    }

    public SalaGrupo findById(Integer id) {
        return this.salaGrupoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<SalaGrupo> list() {
        return this.salaGrupoRepository.findAll();
    }
}
