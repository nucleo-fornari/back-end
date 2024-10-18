package fornari.nucleo.controller;

import fornari.nucleo.domain.dto.restricao.RestricaoCreationRequestDto;
import fornari.nucleo.domain.dto.restricao.RestricaoResponseWithoutAlunosDto;
import fornari.nucleo.domain.entity.Restricao;
import fornari.nucleo.domain.mapper.RestricaoMapper;
import fornari.nucleo.service.RestricaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restricoes")
public class RestricaoController {

    private final RestricaoService service;

    public RestricaoController(RestricaoService restricaoService) {
        this.service = restricaoService;
    }

    @GetMapping
    public ResponseEntity<List<RestricaoResponseWithoutAlunosDto>> list() {
        List<Restricao> restricoes = this.service.list();

        if (restricoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(RestricaoMapper.multipleRestricaoToRestricaoResponseWithoutAlunosDto(restricoes));
    }

    @PostMapping
    public ResponseEntity<RestricaoResponseWithoutAlunosDto> create(@RequestBody RestricaoCreationRequestDto data) {
        return ResponseEntity.status(201).body(RestricaoMapper.restricaoToRestricaoResponseWithoutAlunosDto(
                this.service.create(RestricaoMapper.restricaoCreationRequestDtoToRestricao(data))
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
