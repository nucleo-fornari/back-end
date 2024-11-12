package fornari.nucleo.service;

import fornari.nucleo.domain.dto.EventoCriacaoReqDto;
import fornari.nucleo.domain.dto.EventoRespostaDto;
import fornari.nucleo.domain.entity.Evento;
import fornari.nucleo.domain.entity.Sala;
import fornari.nucleo.domain.entity.Usuario;
import fornari.nucleo.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {
    private final UsuarioService usuarioService;
    private final EventoRepository eventoRepository;
    private final SalaService salaService;

    public Evento criar(Evento entidade, Integer usuarioId) {
        entidade.setUsuario(usuarioService.buscarPorID(usuarioId));
        return eventoRepository.save(entidade);
    }

    public Evento findById(Integer id) {
        return eventoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Evento> listar() {
        return eventoRepository.findAll();
    }

    public List<Evento> listarPublicacoes() {
        return eventoRepository.findAllByTipo("Publicacao");
    }


    public Evento enrollPublicationWithClassroom(Integer id, List<Integer> salas) {
        Evento evento = findById(id);

        for (Sala sala : evento.getSalas()) {
            evento.getSalas().remove(sala);
            sala.getEventos().remove(evento);
        }

        evento.getSalas().clear();

        for (Integer idSala : salas) {
            Sala sala = salaService.findById(idSala);
            evento.addSala(sala);
            sala.addEvento(evento);
        }

        return eventoRepository.save(evento);
    }
}
