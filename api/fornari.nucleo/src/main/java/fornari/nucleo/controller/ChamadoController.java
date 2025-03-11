package fornari.nucleo.controller;

import fornari.nucleo.domain.dto.ChamadoDto;
import fornari.nucleo.domain.mapper.ChamadoMapper;
import fornari.nucleo.service.ChamadoService;
import fornari.nucleo.service.NotificacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/chamados")
@RequiredArgsConstructor
public class ChamadoController {

    private final ChamadoService service;
    private final NotificacaoService notificacaoService;

    @Operation(summary = "Lista todos os Chamados em aberto", description = "Retorna uma lista de todos os Chamados em aberto")
    @ApiResponse(responseCode = "200", description = "Lista de Chamados em aberto",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ChamadoDto.class)))

    @GetMapping("/abertos")
    public ResponseEntity<List<ChamadoDto>> listFinalizadoFalse() {
        List<ChamadoDto> chamadoDtos = service.findByFinalizadoEqualsFalse().stream().map(ChamadoMapper::ToChamadoDto).toList();

        if (chamadoDtos.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(chamadoDtos);
    }

    @Operation(summary = "Lista todos os Chamados", description = "Retorna uma lista de todos os Chamados")
    @ApiResponse(responseCode = "200", description = "Lista de Chamados",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ChamadoDto.class)))

    @GetMapping
    public ResponseEntity<List<ChamadoDto>> listByUser(
            @Parameter(description = "Sala a ser obtida os chamados", required = true) @RequestParam Integer idUser
    ) {
        List<ChamadoDto> chamadoDtos = service.getByIdUser(idUser).stream().map(ChamadoMapper::ToChamadoDto).toList();

        if (chamadoDtos.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(chamadoDtos);
    }

    @Operation(summary = "Cria um novo chamado", description = "Cria um novo chamado com dados específicos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Chamado criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ChamadoDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")})

    @PostMapping
    public ResponseEntity<ChamadoDto> create(
            @Parameter(description = "Dados do Chamado a serem atualizados", required = true) @RequestBody ChamadoDto dto,
            @Parameter(description = "ID do aluno a ser atualizado", required = true) @RequestParam Integer idUsuario) {
        ChamadoDto chamadoDto = ChamadoMapper.ToChamadoDto(service.create(dto, idUsuario));

        notificacaoService.criarNotificacaoParaSecretaria("CHAMADO", "Foi feita uma abertura de chamado!");

        return ResponseEntity.status(201).body(chamadoDto);
    }

    @Operation(summary = "Finaliza o status de um Chamado pelo ID", description = "Atualiza as informações de um chamado existente")
    @ApiResponse(responseCode = "204", description = "Chamado finalizado com sucesso")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> finishChamado(@Parameter(description = "ID do chamado a ser finalizado", required = true) @PathVariable Integer id) {
        this.service.finish(id);
        return ResponseEntity.noContent().build();
    }
}
