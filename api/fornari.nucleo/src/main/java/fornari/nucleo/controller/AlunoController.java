package fornari.nucleo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fornari.nucleo.domain.dto.FiliacaoAlunoDto;
import fornari.nucleo.domain.dto.aluno.AlunoRequestDto;
import fornari.nucleo.domain.dto.aluno.AlunoResponseDto;
import fornari.nucleo.domain.entity.Aluno;
import fornari.nucleo.domain.mapper.AlunoMapper;
import fornari.nucleo.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService service;

    @Operation(summary = "Cria um novo aluno", description = "Cria um novo aluno com dados específicos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Aluno criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AlunoResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")})

    @PostMapping
    public ResponseEntity<AlunoResponseDto> create(
            @Parameter(description = "Dados para criar um novo aluno", required = true) @Valid @RequestParam String body,
            @Parameter(description = "Laudo") @RequestParam MultipartFile laudo
    ) {

        AlunoRequestDto dto;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            dto = objectMapper.readValue(body, AlunoRequestDto.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        Aluno aluno = this.service.create(
                AlunoMapper.alunoCreationRequestDtotoAluno(dto),
                AlunoMapper.responsavelAlunoDtotoUsuario(dto.getFiliacao().getResponsavel()),
                dto.getFiliacao().getParentesco(), dto.getRestricoes(),
                laudo
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(AlunoMapper.AlunotoDto(aluno));
    }

    @Operation(summary = "Lista todos os alunos", description = "Retorna uma lista de todos os alunos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de alunos",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AlunoResponseDto.class)))

    @GetMapping
    public ResponseEntity<List<AlunoResponseDto>> list() {
        return ResponseEntity.ok().body(
                this.service.findAll().stream().map(AlunoMapper::AlunotoDto).toList()
        );
    }

    @Operation(summary = "Obtém um aluno pelo ID", description = "Retorna um aluno com o ID especificado")
    @ApiResponse(responseCode = "200", description = "Aluno encontrado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AlunoResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "Aluno não encontrado")

    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponseDto> getById(
            @Parameter(description = "ID do aluno a ser obtido", required = true) @PathVariable int id
    ) {
        return ResponseEntity.ok().body(AlunoMapper.AlunotoDto(this.service.findById(id)));
    }

    @Operation(summary = "Adiciona um responsável ao aluno", description = "Associa um responsável a um aluno específico")
    @ApiResponse(responseCode = "200", description = "Responsável adicionado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AlunoResponseDto.class)))

    @PutMapping("/{id}/responsavel")
    public ResponseEntity<AlunoResponseDto> addResponsavel(
            @Parameter(description = "ID do aluno", required = true) @PathVariable int id,
            @Parameter(description = "Dados de filiação do responsável", required = true) @RequestBody @Valid FiliacaoAlunoDto data
    ) {
        return ResponseEntity.ok().body(AlunoMapper.AlunotoDto(this.service.addResponsavel(
                AlunoMapper.responsavelAlunoDtotoUsuario(data.getResponsavel()), id, data.getParentesco())));
    }

    @Operation(summary = "Atualiza um aluno pelo ID", description = "Atualiza as informações de um aluno existente")
    @ApiResponse(responseCode = "200", description = "Aluno atualizado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AlunoResponseDto.class)))

    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponseDto> updateAluno(
            @Parameter(description = "ID do aluno a ser atualizado", required = true) @PathVariable int id,
            @Parameter(description = "Dados do aluno a serem atualizados", required = true) @RequestBody AlunoRequestDto body
    ) {
        return ResponseEntity.ok().body(AlunoMapper.AlunotoDto(this.service.update(
                AlunoMapper.alunoCreationRequestDtotoAluno(body), body.getRestricoes(), id)));
    }

    @Operation(summary = "Exclui um aluno pelo ID", description = "Remove um aluno existente pelo ID especificado")
    @ApiResponse(responseCode = "204", description = "Aluno excluído com sucesso")

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do aluno a ser excluído", required = true) @PathVariable int id
    ) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Exclui um responsável específico de um aluno", description = "Remove um responsável associado a um aluno")
    @ApiResponse(responseCode = "204", description = "Responsável excluído com sucesso")

    @DeleteMapping("/{id}/responsavel/{idResponsavel}")
    public ResponseEntity<AlunoResponseDto> delete(
            @Parameter(description = "ID do aluno", required = true) @PathVariable int id,
            @Parameter(description = "ID do responsável a ser excluído", required = true) @PathVariable int idResponsavel) {
        this.service.delete(id, idResponsavel);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}/sala/{salaId}", name = "ENROLL_STUDENT_WITH_CLASSROOM")
    public ResponseEntity<AlunoResponseDto> enrollStudentWithClassroom(
        @PathVariable Integer id,
        @PathVariable Integer salaId
    ) {
        return ResponseEntity.ok().body(AlunoMapper.AlunotoDto(this.service.enrollStudentWithClassroom(id, salaId)));
    }

    @PatchMapping(value = "/{id}/sala/remover")
    public ResponseEntity<AlunoResponseDto> removeStudentFromClassroom(@PathVariable Integer id) {
        return ResponseEntity.ok().body(AlunoMapper.AlunotoDto(this.service.removeStudentFromClassroom(id)));
    }
}
