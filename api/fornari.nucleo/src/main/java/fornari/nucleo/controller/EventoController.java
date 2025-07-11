package fornari.nucleo.controller;

import fornari.nucleo.domain.dto.ChamadoTipoDto;
import fornari.nucleo.domain.dto.EventoCriacaoReqDto;
import fornari.nucleo.domain.dto.EventoRespostaDto;
import fornari.nucleo.domain.dto.restricao.RestricaoResponseWithoutAlunosDto;
import fornari.nucleo.domain.entity.Evento;
import fornari.nucleo.domain.mapper.EventoMapper;
import fornari.nucleo.service.EventoService;
import fornari.nucleo.service.NotificacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;
    private final NotificacaoService notificacaoService;

    @Operation(summary = "Cria um novo Evento", description = "Cria um novo Evento com dados específicos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evento criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ChamadoTipoDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")})
    @PostMapping
    public ResponseEntity<EventoRespostaDto> criar(
            @Parameter(description = "Dados do evento a serem criados", required = true)
            @RequestBody @Valid EventoCriacaoReqDto eventoCriacaoReqDto
            ) {
        Evento evento = eventoService.criar(EventoMapper.toEvento(eventoCriacaoReqDto),
                        eventoCriacaoReqDto.getUsuarioId(), eventoCriacaoReqDto.getSalas());

        List<Integer> listaDeId = evento.getSalas().stream()
                .flatMap(sala -> sala.getAlunos().stream())
                .flatMap(aluno -> aluno.getFiliacoes().stream())
                .map(filiacao -> filiacao.getResponsavel().getId())
                .collect(Collectors.toList());

        List<Integer> professoresIds = evento.getSalas().stream()
                .flatMap(sala -> sala.getProfessores().stream())
                .map(professor -> professor.getId())
                .collect(Collectors.toList());

        listaDeId.addAll(professoresIds);

        notificacaoService.criarNotificacao("EVENTO", "Um evento novo foi criado!", listaDeId);

        return ResponseEntity.created(null).body(EventoMapper.toEventoRespostaDto(evento));
    }

    @Operation(summary = "Lista todos os eventos", description = "Retorna uma lista de eventos")
    @ApiResponse(responseCode = "200", description = "Lista de eventos",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EventoRespostaDto.class)))

    @GetMapping("sala/{id}")
    public ResponseEntity<List<EventoRespostaDto>> listarPorSala(
            @Parameter(description = "ID da sala a ser obtido", required = true)
            @PathVariable int id
    ) {
        return ResponseEntity.ok(
                eventoService.listarPorSala(id).stream().map(EventoMapper::toEventoRespostaDto).toList()
        );
    }

    @Operation(summary = "Lista todos os eventos", description = "Retorna uma lista de eventos")
    @ApiResponse(responseCode = "200", description = "Lista de eventos",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EventoRespostaDto.class)))

    @GetMapping
    public ResponseEntity<List<EventoRespostaDto>> listar() {
        return ResponseEntity.ok(
                eventoService.listar().stream().map(EventoMapper::toEventoRespostaDto).toList()
        );
    }

    @Operation(summary = "Lista todos os eventos do tipo Publicação", description = "Retorna uma lista de eventos do tipo Publicação")
    @ApiResponse(responseCode = "200", description = "Lista de publicações",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EventoRespostaDto.class)))

    @GetMapping("/publicacoes")
    public ResponseEntity<List<EventoRespostaDto>> listarPublicacoes() {
        return ResponseEntity.ok(
                eventoService.listarPublicacoes().stream().map(EventoMapper::toEventoRespostaDto).toList()
        );
    }

    @GetMapping("/publicacoes/usuario/{id}")
    public ResponseEntity<List<EventoRespostaDto>> listPublicationsByUser(@PathVariable Integer id) {
        List<Evento> list = eventoService.findByUserId(id);

        if (list.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(
                list.stream().map(EventoMapper::toEventoRespostaDto).toList()
        );
    }

    @PutMapping(value = "/{id}/sala", name = "ENROLL_PUBLICATION_FROM_CLASSROOM")
    public ResponseEntity<EventoRespostaDto> enrollPublicationWithClassroom(@PathVariable Integer id, @RequestBody List<Integer> salas) {
        return ResponseEntity.ok(EventoMapper.toEventoRespostaDto(eventoService.enrollPublicationWithClassroom(null, id, salas)));
    }

    @DeleteMapping(value = "/{id}", name = "DELETE_EVENT")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        eventoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}", name = "UPDATE_EVENT")
    public ResponseEntity<EventoRespostaDto> update(@PathVariable Integer id, @RequestBody EventoCriacaoReqDto eventoUpdateReqDto) {
        return ResponseEntity.ok(EventoMapper.toEventoRespostaDto(eventoService.update(id, EventoMapper.toEvento(eventoUpdateReqDto), eventoUpdateReqDto.getSalas())));
    }
}
