package fornari.nucleo.controller;

import fornari.nucleo.domain.dto.recado.RecadoRequestDto;
import fornari.nucleo.domain.dto.recado.RecadoResponseDto;
import fornari.nucleo.domain.entity.Recado;
import fornari.nucleo.domain.mapper.RecadoMapper;
import fornari.nucleo.service.RecadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recados")
@RequiredArgsConstructor
public class RecadoController {

    private final RecadoService service;

    @PutMapping("/create/aluno/{idAluno}")
    public ResponseEntity<RecadoResponseDto> create(@PathVariable Integer idAluno, @RequestBody RecadoRequestDto data) {
        return ResponseEntity.status(201).body(RecadoMapper.recadoToRecadoResponseDto(
                this.service.create(RecadoMapper.recadoRequestDtoToRecado(data), idAluno, data.getUsuarioId())));
    }


    @GetMapping
    public ResponseEntity<List<RecadoResponseDto>> list() {
        List<Recado> list = this.service.findAll();

        if (list.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(list.stream().map(RecadoMapper::recadoToRecadoResponseDto).toList());
    }

    @GetMapping("/aluno/{idAluno}")
    public ResponseEntity<List<RecadoResponseDto>> findByAluno(@PathVariable Integer idAluno) {
        List<Recado> list = this.service.findByAluno(idAluno);

        if (list.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(list.stream()
                .map(RecadoMapper::recadoToRecadoResponseDto).toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/responsavel/{id}")
    public ResponseEntity<List<RecadoResponseDto>> getByResponsavelId(@PathVariable Integer id) {
        List<Recado> recados = this.service.findByUserId(id);

        if (recados.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(recados.stream().map(RecadoMapper::recadoToRecadoResponseDto).toList());
    }
}
