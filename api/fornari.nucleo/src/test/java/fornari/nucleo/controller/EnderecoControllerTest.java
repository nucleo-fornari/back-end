package fornari.nucleo.controller;

import fornari.nucleo.domain.dto.EnderecoApiExternaDto;
import fornari.nucleo.domain.dto.EnderecoDto;
import fornari.nucleo.domain.mapper.EnderecoMapper;
import fornari.nucleo.service.EnderecoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários para EnderecoController")
class EnderecoControllerTest {

    @Mock
    private EnderecoService enderecoService;

    @InjectMocks
    private EnderecoController controller;

    @Test
    @DisplayName("Deve retornar um endereço válido pelo CEP")
    void getEnderecoByCep() {
        // Arrange
        String cep = "12345678";
        EnderecoApiExternaDto enderecoApi = new EnderecoApiExternaDto("12345678", "SP", "Bairro B", "Cidade C", "SP");
        EnderecoDto enderecoDto = new EnderecoDto("12345678", "Rua A", "Bairro B", "Cidade C", "SP");

        RestClient clientMock = mock(RestClient.class);
        when(enderecoService.builderRest("https://viacep.com.br/ws/")).thenReturn(clientMock);
        when(clientMock.get().uri(cep + "/json").retrieve().body(EnderecoApiExternaDto.class))
                .thenReturn(enderecoApi);
        when(EnderecoMapper.toEnderecoDto(enderecoApi)).thenReturn(enderecoDto);

        // Act
        ResponseEntity<EnderecoDto> response = controller.get(cep);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("12345678", response.getBody().getCep());
        assertEquals("Rua A", response.getBody().getLogradouro());
    }

    @Test
    @DisplayName("Deve retornar 204 quando o CEP não existir")
    void getEnderecoByCep_NotFound() {
        // Arrange
        String cep = "12345678";
        RestClient clientMock = mock(RestClient.class);

        when(enderecoService.builderRest("https://viacep.com.br/ws/")).thenReturn(clientMock);
        when(clientMock.get().uri(cep + "/json").retrieve().body(EnderecoApiExternaDto.class)).thenReturn(null);

        // Act
        ResponseEntity<EnderecoDto> response = controller.get(cep);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Deve retornar uma lista de endereços por nome da rua")
    void getEnderecoByNomeRua() {
        // Arrange
        String nomeRua = "Rua A";
        List<EnderecoApiExternaDto> enderecoApiList = List.of(
                new EnderecoApiExternaDto("12345678", "Rua A", "Bairro B", "Cidade C", "SP"),
                new EnderecoApiExternaDto("87654321", "Rua A", "Bairro D", "Cidade E", "SP")
        );
        List<EnderecoDto> enderecoDtos = List.of(
                new EnderecoDto("12345678", "Rua A", "Bairro B", "Cidade C", "SP"),
                new EnderecoDto("87654321", "Rua A", "Bairro D", "Cidade E", "SP")
        );

        RestClient clientMock = mock(RestClient.class);
        when(enderecoService.builderRest("https://viacep.com.br/ws/SP/Mauá/")).thenReturn(clientMock);
        when(clientMock.get().uri(nomeRua + "/json").retrieve().body((Class<Object>) any())).thenReturn(enderecoApiList);
        when(enderecoService.organizeByLogradouro(anyList())).thenReturn(enderecoDtos);

        // Act
        ResponseEntity<List<EnderecoDto>> response = controller.getByNomeRua(nomeRua);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Rua A", response.getBody().get(0).getLogradouro());
    }

    @Test
    @DisplayName("Deve retornar 204 quando a lista de endereços por nome da rua estiver vazia")
    void getEnderecoByNomeRua_Empty() {
        // Arrange
        String nomeRua = "Rua Z";
        RestClient clientMock = mock(RestClient.class);

        when(enderecoService.builderRest("https://viacep.com.br/ws/SP/Mauá/")).thenReturn(clientMock);
        when(clientMock.get().uri(nomeRua + "/json").retrieve().body(anyList())).thenReturn(List.of());

        // Act
        ResponseEntity<List<EnderecoDto>> response = controller.getByNomeRua(nomeRua);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}