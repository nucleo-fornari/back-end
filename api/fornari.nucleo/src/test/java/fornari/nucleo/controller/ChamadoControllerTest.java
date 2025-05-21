package fornari.nucleo.controller;

import fornari.nucleo.domain.dto.ChamadoDto;
import fornari.nucleo.domain.dto.ChamadoTipoDto;
import fornari.nucleo.domain.dto.usuario.UsuarioResponseDto;
import fornari.nucleo.domain.mapper.ChamadoMapper;
import fornari.nucleo.service.ChamadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(value = MockitoExtension.class)
@DisplayName("Testes unitários para ChamadoController")
public class ChamadoControllerTest {

    @Mock
    private ChamadoService service;

    @InjectMocks
    private ChamadoController controller;

    @Test
    @DisplayName("Dado que existem chamados em aberto, deve retornar lista de chamados em aberto")
    void listFinalizadoFalse_Sucesso() {
        // Arrange
        List<ChamadoDto> chamados = new ArrayList<>();

        ChamadoTipoDto tipo1 = new ChamadoTipoDto(1, "Saúde do aluno", 1, Collections.emptyList()); // Tipo do Chamado 1
        ChamadoTipoDto tipo2 = new ChamadoTipoDto(2, "Suporte Técnico", 2, Collections.emptyList()); // Tipo do Chamado 2

        UsuarioResponseDto responsavel = new UsuarioResponseDto(); // Supondo que você já tenha definido isso

        chamados.add(new ChamadoDto(
                1,
                "O Joaozinho precisa trocar de fralda",
                false,
                null,
                tipo1,
                null,
                false,
                responsavel));

        chamados.add(new ChamadoDto(
                2,
                "Problema no sistema",
                false,
                null,
                tipo2,
                null,
                false,
                responsavel));


        Mockito.when(service.findByFinalizadoEqualsFalse()).thenReturn(
                chamados.stream().map(ChamadoMapper::toChamado).toList()
        );

        // Act
        ResponseEntity<List<ChamadoDto>> response = controller.listFinalizadoFalse();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        assertEquals(2, response.getBody().size());
        assertEquals("O Joaozinho precisa trocar de fralda", response.getBody().get(0).getDescricao());
    }

    @Test
    @DisplayName("Dado que não existem chamados em aberto, deve retornar NO_CONTENT")
    void listFinalizadoFalse_NenhumChamado() {
        // Arrange
        Mockito.when(service.findByFinalizadoEqualsFalse()).thenReturn(List.of());

        // Act
        ResponseEntity<List<ChamadoDto>> response = controller.listFinalizadoFalse();

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Dado que existem chamados para um usuário, deve retornar lista de chamados")
    void listByUser_Sucesso() {
        // Arrange
        List<ChamadoDto> chamados = new ArrayList<>();

        ChamadoTipoDto tipo1 = new ChamadoTipoDto(1, "Saúde do aluno", 1, Collections.emptyList()); // Tipo do Chamado 1
        ChamadoTipoDto tipo2 = new ChamadoTipoDto(2, "Suporte Técnico", 2, Collections.emptyList()); // Tipo do Chamado 2

        UsuarioResponseDto responsavel = new UsuarioResponseDto(); // Supondo que você já tenha definido isso

        chamados.add(new ChamadoDto(
                null,
                "O Joaozinho precisa trocar de fralda",
                false,
                null,
                tipo1,
                null,
                false,
                responsavel));

        chamados.add(new ChamadoDto(
                null,
                "Problema no sistema",
                false,
                null,
                tipo2,
                null,
                false,
                responsavel));


        Mockito.when(service.getByIdUser(1)).thenReturn(
                chamados.stream().map(ChamadoMapper::toChamado).toList()
        );

        // Act
        ResponseEntity<List<ChamadoDto>> response = controller.listByUser(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("O Joaozinho precisa trocar de fralda", response.getBody().get(0).getDescricao());
    }

    @Test
    @DisplayName("Dado que não existem chamados para um usuário, deve retornar NO_CONTENT")
    void listByUser_NenhumChamado() {
        // Arrange
        Integer idUser = 2;
        Mockito.when(service.getByIdUser(idUser)).thenReturn(List.of());

        // Act
        ResponseEntity<List<ChamadoDto>> response = controller.listByUser(idUser);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

//    @Test
//    @DisplayName("Dado que um novo chamado é criado, deve retornar status CREATED e o chamado")
//    void create_Sucesso() {
//        // Arrange
//        ChamadoTipoDto tipo1 = new ChamadoTipoDto(1, "Saúde do aluno", 1, Collections.emptyList());
//        UsuarioResponseDto responsavel = new UsuarioResponseDto(); // Supondo que você já tenha definido isso
//
//        ChamadoDto chamadoDto = new ChamadoDto(
//                null,
//                "O Joaozinho precisa trocar de fralda",
//                false,
//                null,
//                tipo1,
//                null,
//                false,
//                responsavel);
//
//        Mockito.when(service.create(eq(chamadoDto), eq(1))).thenReturn(ChamadoMapper.toChamado(chamadoDto));
//
//        // Act
//        ResponseEntity<ChamadoDto> response = controller.create(chamadoDto, 1);
//
//        // Assert
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals("O Joaozinho precisa trocar de fralda", response.getBody().getDescricao());
//    }


    @Test
    @DisplayName("Dado que um chamado é finalizado, deve retornar status NO_CONTENT")
    void finishChamado_Sucesso() {
        // Arrange
        Integer idChamado = 1;
        Mockito.doNothing().when(service).finish(idChamado);

        // Act
        ResponseEntity<Void> response = controller.finishChamado(idChamado);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
}