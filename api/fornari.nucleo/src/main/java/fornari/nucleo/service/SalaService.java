package fornari.nucleo.service;

import fornari.nucleo.domain.dto.sala.SalaResponseDto;
import fornari.nucleo.domain.entity.Sala;
import fornari.nucleo.helper.messages.ConstMessages;
import fornari.nucleo.repository.SalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SalaService {
    private final SalaRepository salaRepository;
    private final SalaGrupoService salaGrupoService;

    public List<Sala> list() {
        return salaRepository.findAll();
    }

    public Sala findById(Integer id) {
        Optional<Sala> optsala = salaRepository.findById(id);
        if (optsala.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    ConstMessages.NOT_FOUND_SALA_BY_ID.formatted(id));
        }
        return optsala.get();
    }

    public Sala create(Sala sala, Integer grupoId) {
        sala.setGrupo(salaGrupoService.findById(grupoId));
        return this.salaRepository.save(sala);
    }
}
