package fornari.nucleo.controller;

import fornari.nucleo.domain.dto.restricao.RestricaoCreationRequestDto;
import fornari.nucleo.domain.dto.restricao.RestricaoResponseWithoutAlunosDto;
import fornari.nucleo.domain.entity.Restricao;
import fornari.nucleo.service.RestricaoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários do RestricaoController")
public class RestricaoControllerTest {

    @Mock
    private RestricaoService restricaoService;

    @InjectMocks
    private RestricaoController restricaoController;

    @Test
    @DisplayName("Deve listar todas as restrições com sucesso")
    void listAllRestricoes() {
        // Arrange
        Restricao restricao1 = Restricao.builder()
                .id(1)
                .tipo("Tipo1")
                .build();

        Restricao restricao2 = Restricao.builder()
                .id(2)
                .tipo("Tipo2")
                .build();

        List<Restricao> restricoesMock = List.of(restricao1, restricao2);

        // Mocking o serviço para retornar a lista de restrições
        when(restricaoService.list()).thenReturn(restricoesMock);

        // Act
        ResponseEntity<List<RestricaoResponseWithoutAlunosDto>> response = restricaoController.list();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restricoesMock.size(), response.getBody().size());
    }

    @Test
    @DisplayName("Deve retornar noContent quando não houver restrições")
    void listNoContent() {
        // Arrange
        when(restricaoService.list()).thenReturn(List.of());

        // Act
        ResponseEntity<List<RestricaoResponseWithoutAlunosDto>> response = restricaoController.list();

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Deve criar uma nova restrição com sucesso")
    void createRestricao() {
        // Arrange
        RestricaoCreationRequestDto requestDto = new RestricaoCreationRequestDto();
        requestDto.setTipo("Tipo1");

        Restricao restricaoMock = Restricao.builder()
                .id(1)
                .tipo("Tipo1")
                .build();

        // Mocking o serviço para retornar a restrição criada
        when(restricaoService.create(any(Restricao.class))).thenReturn(restricaoMock);

        // Act
        ResponseEntity<RestricaoResponseWithoutAlunosDto> response = restricaoController.create(requestDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(restricaoMock.getTipo(), response.getBody().getTipo());
    }

    @Test
    @DisplayName("Deve excluir uma restrição com sucesso")
    void deleteRestricao() {
        // Arrange
        Integer id = 1;

        // Mocking o serviço para verificar que a restrição será deletada
        doNothing().when(restricaoService).delete(id);

        // Act
        ResponseEntity<Void> response = restricaoController.delete(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Deve retornar conflito ao tentar criar uma restrição com tipo duplicado")
    void createRestricaoConflito() {
        // Arrange
        RestricaoCreationRequestDto requestDto = new RestricaoCreationRequestDto();
        requestDto.setTipo("Tipo1");

        // Mocking o serviço para lançar um erro de conflito
        when(restricaoService.create(any(Restricao.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.CONFLICT, "Tipo já existe"));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            restricaoController.create(requestDto);
        });

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals("Tipo já existe", exception.getReason());
    }






}
