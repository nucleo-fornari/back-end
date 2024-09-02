package fornari.nucleo.controller;

import fornari.nucleo.dto.EnderecoDto;
import fornari.nucleo.entity.Endereco;
import fornari.nucleo.service.EnderecoService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.Name;

@RestController
@RequestMapping("/enderecos")
//@Tag(name = "Endereço")
public class EnderecoController {
    @Autowired
    private EnderecoService enderecoService;

    @GetMapping(name = "GET_BY_CEP")
    public ResponseEntity<EnderecoDto> get(@RequestParam String cep) { // http://localhost:8080/enderecos?cep=
        EnderecoDto enderecoDto = enderecoService.getEnderecoApi(cep);

        if (enderecoDto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(enderecoDto);
    }
}