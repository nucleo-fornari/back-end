package fornari.nucleo.service;

import fornari.nucleo.domain.dto.sala.SalaResponseDto;
import fornari.nucleo.domain.entity.Sala;
import fornari.nucleo.helper.messages.ConstMessages;
import fornari.nucleo.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class SalaService {
    @Autowired
    private SalaRepository salaRepository;

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

    public Sala create(Sala sala) {
        return this.salaRepository.save(sala);
    }
}
