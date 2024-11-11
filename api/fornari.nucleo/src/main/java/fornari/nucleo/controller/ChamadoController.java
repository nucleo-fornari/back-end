package fornari.nucleo.controller;

import fornari.nucleo.domain.dto.ChamadoDto;
import fornari.nucleo.domain.mapper.ChamadoMapper;
import fornari.nucleo.service.ChamadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/chamados")
@RequiredArgsConstructor
public class ChamadoController {

    private final ChamadoService service;

    @Operation(summary = "Lista todos os Chamados em aberto", description = "Retorna uma lista de todos os Chamados em aberto")
    @ApiResponse(responseCode = "200", description = "Lista de Chamados em aberto",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ChamadoDto.class)))

    @GetMapping("/abertos")
    public ResponseEntity<List<ChamadoDto>> list() {
        return ResponseEntity.ok().body(
                service.findByFinalizadoEqualsFalse().stream().map(ChamadoMapper::ToChamadoDto).toList()
        );
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
        return ResponseEntity.status(201).body(
                ChamadoMapper.ToChamadoDto(service.create(dto, idUsuario))
        );
    }

    @Operation(summary = "Finaliza o status de um Chamado pelo ID", description = "Atualiza as informações de um chamado existente")
    @ApiResponse(responseCode = "204", description = "Chamado finalizado com sucesso")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> finishChamado(@Parameter(description = "ID do chamado a ser finalizado", required = true) @PathVariable Integer id) {
        this.service.finish(id);
        return ResponseEntity.noContent().build();
    }
}
