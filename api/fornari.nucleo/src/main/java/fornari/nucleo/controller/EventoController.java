package fornari.nucleo.controller;

import fornari.nucleo.domain.dto.EventoCriacaoReqDto;
import fornari.nucleo.domain.dto.EventoRespostaDto;
import fornari.nucleo.domain.entity.Evento;
import fornari.nucleo.domain.mapper.EventoMapper;
import fornari.nucleo.service.EventoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/eventos")
@RequiredArgsConstructor
public class EventoController {
    private final EventoService eventoService;

    @PostMapping
    public ResponseEntity<EventoRespostaDto> criar(
            @RequestBody @Valid EventoCriacaoReqDto eventoCriacaoReqDto
            ) {
        return ResponseEntity.created(null).body(
                EventoMapper.toEventoRespostaDto(
                        eventoService.criar(EventoMapper.toEvento(eventoCriacaoReqDto), eventoCriacaoReqDto.getUsuarioId())
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<EventoRespostaDto>> listar() {
        return ResponseEntity.ok(
                eventoService.listar().stream().map(EventoMapper::toEventoRespostaDto).toList()
        );
    }
}
