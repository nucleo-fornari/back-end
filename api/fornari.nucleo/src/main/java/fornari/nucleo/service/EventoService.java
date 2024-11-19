package fornari.nucleo.service;

import fornari.nucleo.domain.dto.EventoCriacaoReqDto;
import fornari.nucleo.domain.dto.EventoRespostaDto;
import fornari.nucleo.domain.entity.Evento;
import fornari.nucleo.domain.entity.Sala;
import fornari.nucleo.domain.entity.Usuario;
import fornari.nucleo.helper.messages.ConstMessages;
import fornari.nucleo.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EventoService {
    private final UsuarioService usuarioService;
    private final EventoRepository eventoRepository;
    private final SalaService salaService;

    public Evento criar(Evento entidade, Integer usuarioId, List<Integer> salas) {
        Usuario user = usuarioService.buscarPorID(usuarioId);

        if ((Objects.equals(user.getFuncao(), "PROFESSOR") && user.getSala() == null) ||
                (Objects.equals(user.getFuncao(), "SECRETARIO") && salas.isEmpty()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, ConstMessages.NOT_ALLOWED_TO_CREATE_EVENT_WITHOUT_CLASS);

        entidade.setUsuario(user);
        if (entidade.getTipo().equals("PUBLICACAO")) {
            entidade.setSalas(new ArrayList<>());
            return enrollPublicationWithClassroom(entidade, null, salas);
        }

        entidade.setSalas(new ArrayList<>(List.of(new Sala[]{user.getSala()})));
        return eventoRepository.save(entidade);
    }

    public Evento findById(Integer id) {
        return eventoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Evento> listar() {
        return eventoRepository.findAll();
    }

    public List<Evento> listarPublicacoes() {
        return eventoRepository.findAllByTipo("PUBLICACAO");
    }


    public Evento enrollPublicationWithClassroom(Evento evento, Integer id, List<Integer> salas) {
        if (evento == null) {
            evento = findById(id);
        }

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

    public List<Evento> findByUserId(Integer id) {
        return eventoRepository.findAllByUsuarioId(id);
    }
}
