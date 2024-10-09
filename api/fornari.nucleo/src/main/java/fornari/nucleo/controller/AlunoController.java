package fornari.nucleo.controller;

import fornari.nucleo.domain.dto.aluno.AlunoCreationRequestDto;
import fornari.nucleo.domain.dto.aluno.AlunoCreationResponseDto;
import fornari.nucleo.domain.entity.Aluno;
import fornari.nucleo.domain.mapper.AlunoMapper;
import fornari.nucleo.service.AlunoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService service;

    public AlunoController (AlunoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AlunoCreationResponseDto> create(@RequestBody AlunoCreationRequestDto body) {
        Aluno aluno = this.service.create(AlunoMapper.toAluno(body));
        return ResponseEntity.ok().body(AlunoMapper.toDto(aluno));
    }
}
