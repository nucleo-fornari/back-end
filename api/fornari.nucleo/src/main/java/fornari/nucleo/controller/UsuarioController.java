package fornari.nucleo.controller;

import fornari.nucleo.domain.dto.aluno.AlunoAndSalaIdDto;
import fornari.nucleo.domain.dto.aluno.AlunoResponseDto;
import fornari.nucleo.domain.dto.usuario.*;
import fornari.nucleo.domain.dto.usuario.professor.ProfessorResponseDto;
import fornari.nucleo.domain.entity.Aluno;
import fornari.nucleo.domain.entity.Filiacao;
import fornari.nucleo.domain.entity.Sala;
import fornari.nucleo.domain.entity.Usuario;
import fornari.nucleo.domain.mapper.AlunoMapper;
import fornari.nucleo.domain.mapper.ProfessorMapper;
import fornari.nucleo.domain.mapper.UsuarioMapper;
import fornari.nucleo.service.AlunoService;
import fornari.nucleo.service.UsuarioService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @Operation(summary = "Obtém um usuário pelo Email e Senha", description = "Retorna um usuário autenticado")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioTokenDto.class)))
    @ApiResponse(responseCode = "404", description = "Email ou senha do usuario nao cadastrado")
    @ApiResponse(responseCode = "401", description = "Falta de autenticação")
    @ApiResponse(responseCode = "403", description = "Acesso foi negado")

    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody @Valid UsuarioLoginDto usuarioLoginDto) {
        return ResponseEntity.ok(
                service.autenticar(usuarioLoginDto.getEmail(), usuarioLoginDto.getSenha())
        );
    }

    @Operation(summary = "Lista todos os Usuários sem Sala atrelada", description = "Retorna uma lista de Usuários sem Sala atrelada")
    @ApiResponse(responseCode = "200", description = "Lista de Usuários",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioResponseDto.class)))

    @GetMapping(value = "/professores",name = "LIST_USERS")
    public ResponseEntity<List<UsuarioResponseDto>> getProfessoresSemSala() {
        List<Usuario> list = service.listarProfessoresSemSala();

        if (list.isEmpty()) return ResponseEntity.status(204).build();

        return ResponseEntity.status(200).body(
                list.stream().map(UsuarioMapper::toDTO).toList()
        );
    }

    @Operation(summary = "Lista todos os Usuários", description = "Retorna uma lista de Usuários")
    @ApiResponse(responseCode = "200", description = "Lista de Usuários",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioResponseDto.class)))

    @GetMapping(name = "LIST_USERS")
    public ResponseEntity<List<UsuarioResponseDto>> getUsers() {
        List<Usuario> list = service.listar();

        if (list.isEmpty()) return ResponseEntity.status(204).build();

        return ResponseEntity.status(200).body(
                list.stream().map(UsuarioMapper::toDTO).toList()
        );
    }

    @Operation(summary = "Obtém um usuário pelo ID", description = "Retorna um usuário com o ID especificado")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")

    @GetMapping(value = "/{id}", name = "GET_USER_BY_ID")
    public ResponseEntity<UsuarioResponseDto> getById(
            @Parameter(description = "ID a ser obtido", required = true) @PathVariable int id
    ) {
        return ResponseEntity.status(200).body(UsuarioMapper.toDTO(service.buscarPorID(id)));
    }

    @Operation(summary = "Cria um novo Usuário", description = "Cria um novo Usuário com dados específicos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")})

    @PostMapping(value = "/funcionario", name = "CREATE_EMPLOYEE")
    public ResponseEntity<UsuarioResponseDto> createEmployee(
            @RequestBody @Valid UsuarioCreateDto userDto
    ) {
        Usuario user = service.createUsuario(UsuarioMapper.toUser(userDto));
        return ResponseEntity.status(201).body(UsuarioMapper.toDTO(user));
    }

    @Operation(summary = "Atualiza um usuário pelo ID", description = "Atualiza as informações de um usuário existente")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioResponseDto.class)))

    @PutMapping(value = "/{id}", name = "UPDATE_USER")
    public ResponseEntity<UsuarioResponseDto> update(
            @Parameter(description = "ID do usuário a ser atualizado", required = true) @PathVariable Integer id,
            @Parameter(description = "Dados do usuário a ser atualizado", required = true) @RequestBody @Valid UsuarioCreateDto userDTO
    ) {
        Usuario user = service.updateUsuario(UsuarioMapper.toUser(userDTO), id);
        return ResponseEntity.ok(UsuarioMapper.toDTO(user));
    }

    @Operation(summary = "Exclui um usuário específico", description = "Remove um usuário pelo ID")
    @ApiResponse(responseCode = "204", description = "Usuario excluido com sucesso")

    @DeleteMapping(value = "/{id}", name = "DELETE_USER")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do usuário a ser deletado", required = true) @PathVariable int id
    ) {
        service.delete(service.buscarPorID(id));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/professor/{id}/sala/{salaId}", name = "ENROLL_TEACHER_WITH_CLASSROOM")
    public ResponseEntity<ProfessorResponseDto> enrollTeacherWithClassroom(
        @PathVariable Integer id,
        @PathVariable Integer salaId
    ) {
        return ResponseEntity.ok(ProfessorMapper.UsuarioToProfessorResponseDto(
                service.enrollTeacherWithClassroom(id, salaId)));
    }

    @PatchMapping(value = "/professor/{id}/sala/remover", name = "REMOVE_TEACHER_FROM_CLASSROOM")
    public ResponseEntity<ProfessorResponseDto> removeTeacherFromClassroom(@PathVariable Integer id) {
        return ResponseEntity.ok(ProfessorMapper.UsuarioToProfessorResponseDto(
                service.removeTeacherFromClassroom(id)));
    }

    @GetMapping("/aluno-e-sala/{id}")
    public ResponseEntity<List<AlunoAndSalaIdDto>> getAlunoAndSalaId(@PathVariable Integer id) {
        Usuario responsavel = service.buscarPorID(id);

        if (responsavel == null || responsavel.getFiliacoes().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        List<AlunoAndSalaIdDto> filhosComSala = responsavel.getFiliacoes().stream()
                .map((Filiacao dto) -> AlunoMapper.toAlunoAndSalaIdDto(dto.getAfiliado())).toList();

        return ResponseEntity.ok(filhosComSala);
    }
}
