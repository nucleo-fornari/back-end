package fornari.nucleo.controller;

import fornari.nucleo.domain.dto.FiliacaoAlunoDto;
import fornari.nucleo.domain.dto.aluno.AlunoCreationRequestDto;
import fornari.nucleo.domain.dto.aluno.AlunoResponseDto;
import fornari.nucleo.domain.dto.usuario.UsuarioDefaultDto;
import fornari.nucleo.domain.entity.Aluno;
import fornari.nucleo.domain.mapper.AlunoMapper;
import fornari.nucleo.domain.mapper.FiliacaoMapper;
import fornari.nucleo.domain.mapper.UsuarioMapper;
import fornari.nucleo.service.AlunoService;
import jakarta.validation.Valid;
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
    public ResponseEntity<AlunoResponseDto> create(@Valid @RequestBody AlunoCreationRequestDto body) {
        Aluno aluno = this.service.create(AlunoMapper.alunoCreationRequestDtotoAluno(body), body.getRestricoes());
        return ResponseEntity.ok().body(AlunoMapper.AlunotoDto(aluno));
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
            @RequestBody FiliacaoAlunoDto data
    ) {
        return ResponseEntity.ok().body(AlunoMapper.AlunotoDto(this.service.addResponsavel(
                FiliacaoMapper.filiacaoAlunoDtoToFiliacao(data), id)));
    }
}
