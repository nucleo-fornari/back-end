package fornari.nucleo.service;

import fornari.nucleo.domain.entity.Agendamento;
import fornari.nucleo.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    public Agendamento buscarPorId(Integer agendamentoId) {
        return agendamentoRepository.findById(agendamentoId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    public Agendamento salvar(Agendamento agendamento) {
        return agendamentoRepository.save(agendamento);
    }


    public List<Agendamento> listar() {
        return agendamentoRepository.findAll();
    }

    public List<Agendamento> buscarPorIdUser(Integer usuarioID) {
        return agendamentoRepository.findAllByResponsavelId(usuarioID);
    }

    public List<Agendamento> buscarPorIdSala(Integer idSala) {
        return agendamentoRepository.findAllBySalaId(idSala);
    }
}
