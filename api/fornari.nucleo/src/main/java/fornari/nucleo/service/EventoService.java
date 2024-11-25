package fornari.nucleo.service;

import fornari.nucleo.domain.entity.Evento;
import fornari.nucleo.domain.entity.Sala;
import fornari.nucleo.domain.entity.Usuario;
import fornari.nucleo.helper.messages.ConstMessages;
import fornari.nucleo.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class EventoService {
    private final UsuarioService usuarioService;
    private final EventoRepository eventoRepository;
    private final SalaService salaService;

    public Evento criar(Evento entidade, Integer usuarioId, List<Integer> salas) {
        Usuario user = usuarioService.buscarPorID(usuarioId);

        if (!List.of("PROFESSOR", "SECRETARIO").contains(user.getFuncao()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ConstMessages.ONLY_TEACHERS_AND_SECRETARIES);

        if ((Objects.equals(user.getFuncao(), "PROFESSOR") && user.getSala() == null) ||
                (Objects.equals(user.getFuncao(), "SECRETARIO") && salas.isEmpty()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, ConstMessages.NOT_ALLOWED_TO_CREATE_EVENT_WITHOUT_CLASS);

        entidade.setUsuario(user);
        if (entidade.getTipo().equals("PUBLICACAO")) {
            entidade.setSalas(new ArrayList<>());
            return enrollPublicationWithClassroom(entidade, null, salas);
        }

        entidade.setSalas(new ArrayList<>(List.of(user.getSala())));
        user.getSala().addEvento(entidade);
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

        Iterator<Sala> iterator = evento.getSalas().iterator();
        while (iterator.hasNext()) {
            Sala sala = iterator.next();
            iterator.remove(); // Remove a sala da coleção
            sala.removeEvento(evento); // Remove o evento da lista de eventos da sala
        }

        evento.getSalas().clear(); // Limpa a lista de salas

        for (Integer idSala : salas) {
            Sala sala = salaService.findById(idSala);
            evento.addSala(sala);
            sala.addEvento(evento); // Adiciona o evento à lista de eventos da sala
        }

        return eventoRepository.save(evento);
    }

    public List<Evento> findByUserId(Integer id) {
        return eventoRepository.findAllByUsuarioIdOrderByDtPublicacaoDesc(id);
    }

    public List<Evento> listarPorSala(int id) {
        return eventoRepository.findBySalas_Id(id);
    }

    public void delete(Integer id) {
        Evento evento = findById(id);
        for (Sala sala : evento.getSalas()) {
            sala.removeEvento(evento);
        }

        evento.getUsuario().removeEvento(evento);
        evento.setUsuario(null);
        evento.setSalas(new ArrayList<>());
        eventoRepository.delete(evento);
    }

    public Evento update(Integer id, Evento body, List<Integer> salas) {
        Evento evento = findById(id);
        evento.setTitulo(body.getTitulo());
        evento.setData(body.getData());
        evento.setDescricao(body.getDescricao());


        return this.enrollPublicationWithClassroom(evento, id, salas);
    }
}
