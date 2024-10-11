package fornari.nucleo.service;

import fornari.nucleo.domain.dto.EventoCriacaoReqDto;
import fornari.nucleo.domain.dto.EventoRespostaDto;
import fornari.nucleo.domain.entity.Evento;
import fornari.nucleo.domain.entity.Usuario;
import fornari.nucleo.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {
    private final UsuarioService usuarioService;
    private final EventoRepository eventoRepository;

    public Evento criar(Evento entidade, Integer usuarioId) {
        entidade.setUsuario(usuarioService.buscarPorID(usuarioId));
        return eventoRepository.save(entidade);
    }

    public List<Evento> listar() {
        return eventoRepository.findAll();
    }
}
