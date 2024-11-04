package fornari.nucleo.controller;

import fornari.nucleo.domain.dto.FiliacaoAlunoDto;
import fornari.nucleo.domain.dto.aluno.AlunoRequestDto;
import fornari.nucleo.domain.dto.aluno.AlunoResponseDto;
import fornari.nucleo.domain.entity.Aluno;
import fornari.nucleo.domain.mapper.AlunoMapper;
import fornari.nucleo.domain.mapper.FiliacaoMapper;
import fornari.nucleo.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService service;

    public AlunoController (AlunoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AlunoResponseDto> create(@Valid @RequestBody AlunoRequestDto body) {
        Aluno aluno = this.service.create(
                AlunoMapper.alunoCreationRequestDtotoAluno(body),
                AlunoMapper.responsavelAlunoDtotoUsuario(body.getFiliacao().getResponsavel()),
                body.getFiliacao().getParentesco(), body.getRestricoes()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(AlunoMapper.AlunotoDto(aluno));
    }

    @GetMapping
    public ResponseEntity<List<AlunoResponseDto>> list() {
        return ResponseEntity.ok().body(
                this.service.findAll().stream().map(AlunoMapper::AlunotoDto).toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponseDto> getById(@PathVariable int id) {
        return ResponseEntity.ok().body(AlunoMapper.AlunotoDto(this.service.findById(id)));
    }

    @PutMapping("/{id}/responsavel")
    public ResponseEntity<AlunoResponseDto> addResponsavel(
            @PathVariable int id,
            @RequestBody @Valid FiliacaoAlunoDto data
    ) {
        return ResponseEntity.ok().body(AlunoMapper.AlunotoDto(this.service.addResponsavel(
                AlunoMapper.responsavelAlunoDtotoUsuario(data.getResponsavel()), id, data.getParentesco())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponseDto> updateAluno(
            @PathVariable int id,
            @RequestBody AlunoRequestDto body
    ) {
        return ResponseEntity.ok().body(AlunoMapper.AlunotoDto(this.service.update(
                AlunoMapper.alunoCreationRequestDtotoAluno(body), body.getRestricoes(), id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/responsavel/{idResponsavel}")
    public ResponseEntity<AlunoResponseDto> delete(@PathVariable int id, @PathVariable int idResponsavel) {
        return ResponseEntity.ok().body(AlunoMapper.AlunotoDto(this.service.delete(id, idResponsavel)));
    }
}
