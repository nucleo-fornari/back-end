package fornari.nucleo.controller;

import fornari.nucleo.domain.dto.EnderecoApiExternaDto;
import fornari.nucleo.domain.dto.EnderecoDto;
import fornari.nucleo.domain.mapper.EnderecoMapper;
import fornari.nucleo.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoService enderecoService;

    @Operation(summary = "Lista de Endereços por CEP", description = "Retorna uma lista de todos os Endereços por CEP")
    @ApiResponse(responseCode = "200", description = "Lista de endereços",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EnderecoDto.class)))

    @GetMapping(name = "GET_ENDERECO_BY_CEP")
    public ResponseEntity<EnderecoDto> get(
            @Parameter(description = "CEP para pesquisa", required = true) @RequestParam String cep
    ) { // http://localhost:8080/enderecos?cep=

        RestClient client = enderecoService.builderRest("https://viacep.com.br/ws/");

        EnderecoApiExternaDto enderecoApiExternaDto = client.get()
                .uri(cep + "/json")
                .retrieve()
                .body(EnderecoApiExternaDto.class);

        if (enderecoApiExternaDto == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(EnderecoMapper.toEnderecoDto(enderecoApiExternaDto));
    }

    @Operation(summary = "Lista de Endereços pelo nome da rua", description = "Retorna uma lista de todos os Endereços pela rua")
    @ApiResponse(responseCode = "200", description = "Lista de endereços",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EnderecoDto.class)))

    @GetMapping(value = "/rua", name = "GET_LIST_BY_NOME_RUA")
    public ResponseEntity<List<EnderecoDto>> getByNomeRua(
            @Parameter(description = "Rua para pesquisa", required = true)@RequestParam String nomeRua
    ) { // http://localhost:8080/enderecos?cep=

        RestClient client = enderecoService.builderRest("https://viacep.com.br/ws/SP/Mauá/");

        List<EnderecoApiExternaDto> listEnderecoApi = client.get()
                .uri(nomeRua + "/json")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        if (listEnderecoApi.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EnderecoDto> lista = listEnderecoApi.stream().map(EnderecoMapper::toEnderecoDto).toList();

        return ResponseEntity.ok(enderecoService.organizeByLogradouro(lista));
    }
}