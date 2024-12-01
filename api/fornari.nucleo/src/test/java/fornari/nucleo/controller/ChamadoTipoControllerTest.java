package fornari.nucleo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fornari.nucleo.domain.dto.ChamadoTipoDto;
import fornari.nucleo.domain.entity.ChamadoTipo;
import fornari.nucleo.domain.mapper.ChamadoTipoMapper;
import fornari.nucleo.service.ChamadoTipoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários para ChamadoTipoController")
class ChamadoTipoControllerTest {

    @Mock
    private ChamadoTipoService service;

    @InjectMocks
    private ChamadoTipoController controller;

    @Test
    @DisplayName("Deve criar um novo tipo de chamado com sucesso")
    void createChamadoTipo() {
        // Arrange
        ChamadoTipoDto chamadoTipoRequest = new ChamadoTipoDto(null, "Saúde do aluno", 1, null);
        ChamadoTipo chamadoTipo = new ChamadoTipo();
        chamadoTipo.setId(1);
        chamadoTipo.setTipo("Saúde do aluno");
        chamadoTipo.setPrioridade(1);
        ChamadoTipoDto chamadoTipoResponse = new ChamadoTipoDto(1, "Saúde do aluno", 1, null);

        when(service.create(any(ChamadoTipoDto.class))).thenReturn(chamadoTipo);

        // Act
        ResponseEntity<ChamadoTipoDto> response = controller.create(chamadoTipoRequest);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(chamadoTipoResponse.getId(), response.getBody().getId());
        assertEquals("Saúde do aluno", response.getBody().getTipo());
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia com status 204 ao listar tipos de chamados sem registros")
    void listChamadoTipos_Empty() {
        // Arrange
        when(service.listar()).thenReturn(List.of());

        // Act
        ResponseEntity<List<ChamadoTipoDto>> response = controller.list();

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Deve retornar uma lista de tipos de chamados com sucesso")
    void listChamadoTipos() {
        // Arrange
        ChamadoTipo chamadoTipo = new ChamadoTipo();
        chamadoTipo.setId(1);
        chamadoTipo.setTipo("Saúde do aluno");
        chamadoTipo.setPrioridade(1);
        ChamadoTipo chamadoTipo2 = new ChamadoTipo();
        chamadoTipo2.setId(1);
        chamadoTipo2.setTipo("Suporte Técnico");
        chamadoTipo2.setPrioridade(1);

        List<ChamadoTipo> chamadoTipos = List.of(
                chamadoTipo, chamadoTipo2
        );

        List<ChamadoTipoDto> chamadoTipoDtos = List.of(
                new ChamadoTipoDto(1, "Saúde do aluno", 1, null),
                new ChamadoTipoDto(2, "Suporte Técnico", 2, null)
        );

        when(service.listar()).thenReturn(chamadoTipos);

        // Act
        ResponseEntity<List<ChamadoTipoDto>> response = controller.list();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Saúde do aluno", response.getBody().get(0).getTipo());
        assertEquals("Suporte Técnico", response.getBody().get(1).getTipo());
    }
}