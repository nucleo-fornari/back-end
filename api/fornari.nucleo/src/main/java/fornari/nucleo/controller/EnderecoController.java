package fornari.nucleo.controller;

import fornari.nucleo.dto.EnderecoApiExternaDto;
import fornari.nucleo.dto.EnderecoDto;
import fornari.nucleo.entity.Endereco;
import fornari.nucleo.service.EnderecoService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import javax.naming.Name;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/enderecos")
//@Tag(name = "Endereço")
public class EnderecoController {
    @Autowired
    private EnderecoService enderecoService;

    @GetMapping(name = "GET_ENDERECO_BY_CEP")
    public ResponseEntity<EnderecoDto> get(@RequestParam String cep) { // http://localhost:8080/enderecos?cep=

        RestClient client = enderecoService.builderRest("https://viacep.com.br/ws/");

        EnderecoApiExternaDto enderecoApiExternaDto = client.get()
                .uri(cep + "/json")
                .retrieve()
                .body(EnderecoApiExternaDto.class);

        if (enderecoApiExternaDto == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(enderecoService.mapEnderecoApiToEndereco(enderecoApiExternaDto));
    }

    @GetMapping(value = "/rua", name = "GET_LIST_BY_NOME_RUA")
    public ResponseEntity<List<EnderecoDto>> getByNomeRua(@RequestParam String nomeRua) { // http://localhost:8080/enderecos?cep=

        RestClient client = enderecoService.builderRest("https://viacep.com.br/ws/SP/Mauá/");
        List<EnderecoApiExternaDto> listEnderecoApi = client.get()
                .uri(nomeRua + "/json")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        if (listEnderecoApi.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(enderecoService.mapListEnderecoApiToEndereco(listEnderecoApi));
    }
}