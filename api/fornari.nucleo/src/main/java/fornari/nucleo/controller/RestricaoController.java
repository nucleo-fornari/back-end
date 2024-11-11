package fornari.nucleo.controller;

import fornari.nucleo.domain.dto.restricao.RestricaoCreationRequestDto;
import fornari.nucleo.domain.dto.restricao.RestricaoResponseWithoutAlunosDto;
import fornari.nucleo.domain.entity.Restricao;
import fornari.nucleo.domain.mapper.RestricaoMapper;
import fornari.nucleo.service.RestricaoService;
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
@RequestMapping("/restricoes")
@RequiredArgsConstructor
public class RestricaoController {

    private final RestricaoService service;

    @Operation(summary = "Lista todos as Restrições", description = "Retorna uma lista das Restrições")
    @ApiResponse(responseCode = "200", description = "Lista das Restrições",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = RestricaoResponseWithoutAlunosDto.class)))

    @GetMapping
    public ResponseEntity<List<RestricaoResponseWithoutAlunosDto>> list() {
        List<Restricao> restricoes = this.service.list();

        if (restricoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(RestricaoMapper.multipleRestricaoToRestricaoResponseWithoutAlunosDto(restricoes));
    }

    @Operation(summary = "Cria uma nova Restrição", description = "Cria uma nova restrição com dados específicos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restrição criada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestricaoResponseWithoutAlunosDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")})

    @PostMapping
    public ResponseEntity<RestricaoResponseWithoutAlunosDto> create(
            @Parameter(description = "Dados da restrição a serem criado", required = true)
            @RequestBody RestricaoCreationRequestDto data
    ) {
        return ResponseEntity.status(201).body(RestricaoMapper.restricaoToRestricaoResponseWithoutAlunosDto(
                this.service.create(RestricaoMapper.restricaoCreationRequestDtoToRestricao(data))
        ));
    }

    @Operation(summary = "Exclui uma restrição específica", description = "Remove uma restrição pelo ID")
    @ApiResponse(responseCode = "204", description = "Restrição excluída com sucesso")

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete
            (@Parameter(description = "ID da restrição a ser deletada", required = true ) @PathVariable Integer id)
    {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
