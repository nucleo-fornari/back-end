package fornari.nucleo.controller;

import fornari.nucleo.domain.dto.EnderecoDto;
import fornari.nucleo.domain.dto.usuario.UsuarioCreateDto;
import fornari.nucleo.domain.dto.usuario.UsuarioResponseDto;
import fornari.nucleo.domain.dto.usuario.professor.ProfessorResponseDto;
import fornari.nucleo.domain.entity.Endereco;
import fornari.nucleo.domain.entity.Sala;
import fornari.nucleo.domain.entity.SalaGrupo;
import fornari.nucleo.domain.entity.Usuario;
import fornari.nucleo.domain.mapper.EnderecoMapper;
import fornari.nucleo.domain.mapper.UsuarioMapper;
import fornari.nucleo.helper.messages.ConstMessages;
import fornari.nucleo.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários do UsuarioController")
public class UsuarioControllerTest {
    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @Test
    @DisplayName("Deve retornar uma lista dos usuários")
    void testListarUsuarios() {
        // Arrange
        Usuario user1 = Usuario.builder()
                .id(1)
                .nome("Usuario 1")
                .cpf("123456")
                .email("usuario@email.com")
                .funcao("PROFESSOR")
                .telefone("1199999999")
                .filiacoes(Collections.emptyList())
                .endereco(new Endereco())
                .build();

        Usuario user2 = Usuario.builder()
                .id(2)
                .nome("Usuario 2")
                .cpf("789456")
                .email("usuario2@email.com")
                .funcao("SECRETARIO")
                .telefone("1199999999")
                .filiacoes(Collections.emptyList())
                .endereco(new Endereco())
                .build();

        List<Usuario> usuarios = List.of(user1, user2);

        when(usuarioService.listar()).thenReturn(usuarios);

        // Act
        ResponseEntity<List<UsuarioResponseDto>> response = usuarioController.getUsers();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(usuarioService, times(1)).listar();
    }

    @Test
    @DisplayName("Deve criar o usuário com sucesso")
    void testCreateUsuario() {
        // Arrange
        UsuarioCreateDto createDto = new UsuarioCreateDto();
        createDto.setNome("Usuario 1");
        createDto.setCpf("123456");
        createDto.setEmail("usuario@email.com");
        createDto.setTelefone("1199999999");
        createDto.setFuncao("SECRETARIO");
        createDto.setEndereco(new EnderecoDto());

        Usuario resposta = UsuarioMapper.toUser(createDto);
        resposta.setId(1);

        when(usuarioService.createUsuario(any(Usuario.class))).thenReturn(resposta);

        // Act
        ResponseEntity<UsuarioResponseDto> response = usuarioController.createEmployee(createDto);

        // Assert
        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Usuario 1", response.getBody().getNome());
        verify(usuarioService, times(1)).createUsuario(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve atualizar um usuário com sucesso")
    void testUpdateUsuario() {
        // Arrange
        Usuario user1 = Usuario.builder()
                .id(1)
                .nome("Usuario 1")
                .cpf("123456")
                .email("usuario@email.com")
                .funcao("PROFESSOR")
                .telefone("1199999999")
                .filiacoes(Collections.emptyList())
                .endereco(new Endereco())
                .build();

        UsuarioCreateDto createDto = new UsuarioCreateDto();
        createDto.setNome("Usuario 1");
        createDto.setCpf("123456");
        createDto.setEmail("usuario@email.com");
        createDto.setFuncao("SECRETARIO");
        createDto.setEndereco(EnderecoMapper.toEnderecoDto(user1.getEndereco()));

        when(usuarioService.updateUsuario(any(Usuario.class), eq(user1.getId()))).thenReturn(user1);

        // Act
        ResponseEntity<UsuarioResponseDto> response = usuarioController.update(user1.getId(), createDto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Usuario 1", response.getBody().getNome());
        verify(usuarioService, times(1)).updateUsuario(any(Usuario.class), eq(user1.getId()));
    }

    @Test
    @DisplayName("Deve deletar um usuário com sucesso")
    void testDeleteUsuario() {
        // Arrange
        Usuario user1 = Usuario.builder()
                .id(1)
                .nome("Usuario 1")
                .cpf("123456")
                .email("usuario@email.com")
                .funcao("PROFESSOR")
                .telefone("1199999999")
                .filiacoes(Collections.emptyList())
                .endereco(new Endereco())
                .build();

        when(usuarioService.buscarPorID(user1.getId())).thenReturn(user1);

        // Act
        ResponseEntity<Void> response = usuarioController.delete(user1.getId());

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        verify(usuarioService, times(1)).delete(user1);
    }

    @Test
    @DisplayName("Deve atribuir um professor a uma sala corretamente")
    void testEnrollTeacherWithClassroom() {
        // Arrange
        Usuario user1 = Usuario.builder()
                .id(1)
                .nome("Usuario 1")
                .cpf("123456")
                .email("usuario@email.com")
                .funcao("PROFESSOR")
                .telefone("1199999999")
                .filiacoes(Collections.emptyList())
                .endereco(new Endereco())
                .build();

        Sala sala = Sala.builder()
                .id(1)
                .nome("Sala 1")
                .grupo(new SalaGrupo())
                .alunos(Collections.emptyList())
                .build();


        when(usuarioService.enrollTeacherWithClassroom(user1.getId(), sala.getId())).thenReturn(user1);

        // Act
        ResponseEntity<ProfessorResponseDto> response = usuarioController.enrollTeacherWithClassroom(user1.getId(), sala.getId());

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Usuario 1", response.getBody().getNome());
        assertEquals("PROFESSOR", response.getBody().getFuncao());
        verify(usuarioService, times(1)).enrollTeacherWithClassroom(user1.getId(), sala.getId());
    }

    @Test
    @DisplayName("Deve retornar BAD_REQUEST ao tentar atribuir um usuário que não é professor a uma sala")
    void testEnrollNonTeacherWithClassroom() {
        // Arrange
        Usuario user1 = Usuario.builder()
                .id(2)
                .nome("Usuario 2")
                .cpf("654321")
                .email("usuario2@email.com")
                .funcao("RESPONSAVEL")
                .telefone("1198888888")
                .filiacoes(Collections.emptyList())
                .endereco(new Endereco())
                .build();

        Sala sala = Sala.builder()
                .id(1)
                .nome("Sala 1")
                .grupo(new SalaGrupo())
                .alunos(Collections.emptyList())
                .build();

        when(usuarioService.enrollTeacherWithClassroom(user1.getId(), sala.getId()))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, ConstMessages.USER_NOT_PROFESSOR));

        // Act
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> usuarioController.enrollTeacherWithClassroom(user1.getId(), sala.getId())
        );

        // Assert
        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals(ConstMessages.USER_NOT_PROFESSOR, exception.getReason());
        verify(usuarioService, times(1)).enrollTeacherWithClassroom(user1.getId(), sala.getId());
    }







}
