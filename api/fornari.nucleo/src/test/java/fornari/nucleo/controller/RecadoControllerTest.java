package fornari.nucleo.controller;

import fornari.nucleo.domain.dto.recado.RecadoRequestDto;
import fornari.nucleo.domain.dto.recado.RecadoResponseDto;
import fornari.nucleo.domain.entity.*;
import fornari.nucleo.service.RecadoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários do RecadoController")
class RecadoControllerTest {

    @Mock
    private RecadoService recadoService;

    @InjectMocks
    private RecadoController recadoController;

    @Test
    @DisplayName("Deve criar um recado com sucesso")
    void createRecado() {
        // Arrange
        Integer alunoId = 1;
        Integer usuarioId = 2;
        RecadoRequestDto recadoRequestDto = RecadoRequestDto.builder()
                .usuarioId(usuarioId)
                .build();
        Recado recadoMock = new Recado();
        recadoMock.setId(1);
        recadoMock.setDtCriacao(java.time.LocalDateTime.now());
        recadoMock.setAluno(new Aluno());

        RecadoResponseDto recadoResponseDto = new RecadoResponseDto();
        recadoResponseDto.setId(1);

        when(recadoService.create(any(Recado.class), eq(alunoId), eq(usuarioId))).thenReturn(recadoMock);

        // Act
        ResponseEntity<RecadoResponseDto> response = recadoController.create(alunoId, recadoRequestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    @DisplayName("Deve listar todos os recados com sucesso")
    void listAllRecados() {
        // Arrange
        Recado recado1 = Recado.builder()
                .id(1)
                .titulo("Teste1")
                .conteudo("Conteudo1")
                .aluno(Aluno.builder()
                        .id(1)
                        .ra("1234567")
                        .nome("Felipe")
                        .build())
                .build();

        Recado recado2 = Recado.builder()
                .id(2)
                .titulo("Teste2")
                .conteudo("Conteudo2")
                .aluno(Aluno.builder()
                        .id(2)
                        .ra("895421")
                        .nome("Breno")
                        .build())
                .build();
        List<Recado> recadosMock = new ArrayList<>();
        recadosMock.add(recado1);
        recadosMock.add(recado2);

        when(recadoService.findAll()).thenReturn(recadosMock);

        // Act
        ResponseEntity<List<RecadoResponseDto>> response = recadoController.list();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(recadosMock.size(), response.getBody().size());
    }

    @Test
    @DisplayName("Deve retornar No Content quando não houver recados para um aluno")
    void findByAlunoNoContent() {
        // Arrange
        Integer alunoId = 1;
        when(recadoService.findByAluno(alunoId)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<RecadoResponseDto>> response = recadoController.findByAluno(alunoId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Deve deletar um recado com sucesso")
    void deleteRecado() {
        // Arrange
        Integer recadoId = 1;
        doNothing().when(recadoService).delete(recadoId);

        // Act
        ResponseEntity<Void> response = recadoController.delete(recadoId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Deve listar recados por responsavel com sucesso")
    void findByResponsavelId() {
        // Arrange
        Usuario responsavel = Usuario.builder()
                .id(1)
                .nome("Felipe")
                .cpf("1234567")
                .dtNasc(LocalDate.now())
                .filiacoes(Collections.emptyList())
                .endereco(new Endereco())
                .build();

        Recado recado = Recado.builder()
                .titulo("Titulo")
                .conteudo("Conteudo")
                .responsavel(responsavel)
                .id(1)
                .aluno(new Aluno())
                .build();

        Recado recado2 = Recado.builder()
                .titulo("Titulo2")
                .conteudo("Conteudo2")
                .responsavel(responsavel)
                .id(2)
                .aluno(new Aluno())
                .build();

        List<Recado> recadosMock = new ArrayList<Recado>();
        recadosMock.add(recado);
        recadosMock.add(recado2);
        when(recadoService.findByUserId(responsavel.getId())).thenReturn(recadosMock);

        // Act
        ResponseEntity<List<RecadoResponseDto>> response = recadoController.getByResponsavelId(responsavel.getId());

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(recadosMock.size(), response.getBody().size());
    }


    @Test
    @DisplayName("Deve retornar No Content quando não houver recados para um responsavel")
    void findByResponsavelNoContent() {
        // Arrange
        Integer usuarioId = 1;
        when(recadoService.findByUserId(usuarioId)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<RecadoResponseDto>> response = recadoController.getByResponsavelId(usuarioId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
