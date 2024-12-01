package fornari.nucleo.controller;

import fornari.nucleo.domain.dto.sala.SalaRequestDto;
import fornari.nucleo.domain.dto.sala.SalaResponseDto;
import fornari.nucleo.domain.dto.sala.grupo.SalaGrupoResponseDto;
import fornari.nucleo.domain.entity.Sala;
import fornari.nucleo.domain.entity.SalaGrupo;
import fornari.nucleo.service.SalaGrupoService;
import fornari.nucleo.service.SalaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários do SalaController")
public class SalaControllerTest {
    @Mock
    private SalaService salaService;

    @Mock
    private SalaGrupoService salaGrupoService;

    @InjectMocks
    private SalaController salaController;

    @Test
    @DisplayName("Deve criar uma nova sala com sucesso")
    void createSala() {
        // Arrange

        SalaGrupo grupo = new SalaGrupo();
        grupo.setId(1);
        grupo.setNome("G1");
        grupo.setSalas(Collections.emptyList());

        SalaRequestDto salaRequestDto = SalaRequestDto.builder()
                .nome("Sala A")
                .grupoId(1)
                .build();

        Sala salaMock = Sala.builder()
                .id(1)
                .nome("Sala A")
                .grupo(grupo)  // Será configurado na service
                .professores(Collections.emptyList())
                .build();

        when(salaService.create(any(Sala.class), eq(1))).thenReturn(salaMock);

        // Act
        ResponseEntity<SalaResponseDto> response = salaController.create(salaRequestDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(salaMock.getNome(), response.getBody().getNome());
        assertEquals(grupo.getNome(), response.getBody().getGrupo().getNome());
    }

    @Test
    @DisplayName("Deve listar todas as salas com sucesso")
    void listAllSalas() {
        // Arrange
        SalaGrupo grupo = new SalaGrupo();
        grupo.setId(1);
        grupo.setNome("G1");
        grupo.setSalas(Collections.emptyList());

        Sala sala1 = Sala.builder()
                .id(1)
                .nome("Sala A")
                .grupo(grupo)
                .professores(Collections.emptyList())
                .build();

        Sala sala2 = Sala.builder()
                .id(2)
                .nome("Sala B")
                .grupo(grupo)
                .professores(Collections.emptyList())
                .build();

        List<Sala> salasMock = Arrays.asList(sala1, sala2);
        when(salaService.list()).thenReturn(salasMock);

        // Act
        ResponseEntity<List<SalaResponseDto>> response = salaController.list();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(salasMock.size(), response.getBody().size());
    }

    @Test
    @DisplayName("Deve retornar uma sala pelo ID com sucesso")
    void getSalaById() {
        // Arrange
        SalaGrupo grupo = new SalaGrupo();
        grupo.setId(1);
        grupo.setNome("G1");
        grupo.setSalas(Collections.emptyList());

        Sala salaMock = Sala.builder()
                .id(1)
                .nome("Sala A")
                .grupo(grupo)
                .professores(Collections.emptyList())
                .build();

        when(salaService.findById(1)).thenReturn(salaMock);

        // Act
        ResponseEntity<SalaResponseDto> response = salaController.getById(1);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(salaMock.getNome(), response.getBody().getNome());
    }

    @Test
    @DisplayName("Deve criar um novo grupo de sala com sucesso")
    void createSalaGrupo() {
        // Arrange
        SalaGrupoResponseDto salaGrupoResponseDto = SalaGrupoResponseDto.builder()
                .id(1)
                .nome("Grupo A")
                .build();

        when(salaGrupoService.create("Grupo A")).thenReturn(SalaGrupo.builder()
                .id(1)
                .nome("Grupo A")
                .build());

        // Act
        ResponseEntity<SalaGrupoResponseDto> response = salaController.createSalaGrupo("Grupo A");

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(salaGrupoResponseDto.getNome(), response.getBody().getNome());
    }

    @Test
    @DisplayName("Deve listar todos os grupos de sala com sucesso")
    void listSalaGrupos() {
        // Arrange
        SalaGrupo salaGrupo1 = SalaGrupo.builder()
                .id(1)
                .nome("Grupo A")
                .build();

        SalaGrupo salaGrupo2 = SalaGrupo.builder()
                .id(2)
                .nome("Grupo B")
                .build();

        List<SalaGrupo> gruposMock = Arrays.asList(salaGrupo1, salaGrupo2);
        when(salaGrupoService.list()).thenReturn(gruposMock);

        // Act
        ResponseEntity<List<SalaGrupoResponseDto>> response = salaController.listSalaGrupos();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(gruposMock.size(), response.getBody().size());
    }

    @Test
    @DisplayName("Deve excluir uma sala com sucesso")
    void deleteSala() {
        // Arrange
        int salaId = 1;
        doNothing().when(salaService).delete(salaId);

        // Act
        ResponseEntity<Void> response = salaController.deleteSala(salaId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }





}
