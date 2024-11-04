package fornari.nucleo.service;

import fornari.nucleo.domain.entity.Restricao;
import fornari.nucleo.helper.messages.ConstMessages;
import fornari.nucleo.repository.RestricaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RestricaoService {

    private final RestricaoRepository repository;

    public RestricaoService (RestricaoRepository repository) {
        this.repository = repository;
    }

    public Restricao create(Restricao data) {
        if (repository.existsByTipo(data.getTipo())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,ConstMessages.ALREADY_EXISTS_RESTRICAO_BY_TIPO.formatted(data.getTipo()));
        }

        return this.repository.save(data);
    }

    public List<Restricao> list()  {
        return this.repository.findAll();
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        this.repository.deleteById(id);
    }

    public Restricao findById(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ConstMessages.NOT_FOUND_RESTRICAO_BY_ID.formatted(id));
        }

        return this.repository.findById(id).get();
    }
}
