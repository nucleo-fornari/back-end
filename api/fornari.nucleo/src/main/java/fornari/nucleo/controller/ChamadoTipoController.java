package fornari.nucleo.controller;

import fornari.nucleo.domain.dto.ChamadoTipoDto;
import fornari.nucleo.domain.entity.ChamadoTipo;
import fornari.nucleo.domain.mapper.ChamadoTipoMapper;
import fornari.nucleo.service.ChamadoTipoService;
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
@RequestMapping("/tipos-chamado")
@RequiredArgsConstructor
public class ChamadoTipoController {

    private final ChamadoTipoService service;

    @Operation(summary = "Cria um novo tipo de chamado", description = "Cria um novo tipo de chamado com dados específicos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de Chamado criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ChamadoTipoDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")})

    @PostMapping
    public ResponseEntity<ChamadoTipoDto> create(
            @Parameter(description = "Dados do Tipo de chamado para criar") @RequestBody ChamadoTipoDto chamadoTipo
    ) {
        return ResponseEntity.created(null).body(
                ChamadoTipoMapper.toChamadoTipoDto(
                        service.create(chamadoTipo)
                )
        );
    }

    @Operation(summary = "Lista todos os Tipos de Chamados", description = "Retorna uma lista de todos os Tipos de Chamados")
    @ApiResponse(responseCode = "200", description = "Lista de Tipos de Chamados",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ChamadoTipoDto.class)))

    @GetMapping
    public ResponseEntity<List<ChamadoTipoDto>> list() {
        List<ChamadoTipo> list = this.service.listar();

        if (list.isEmpty()) return ResponseEntity.status(204).build();

        return ResponseEntity.status(200).body(
                list.stream().map(ChamadoTipoMapper::toChamadoTipoDto).toList()
        );
    }
}