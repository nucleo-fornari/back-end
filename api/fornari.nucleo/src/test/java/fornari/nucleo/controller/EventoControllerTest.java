package fornari.nucleo.controller;


import fornari.nucleo.domain.dto.EventoCriacaoReqDto;
import fornari.nucleo.domain.dto.EventoRespostaDto;
import fornari.nucleo.domain.entity.Endereco;
import fornari.nucleo.domain.entity.Evento;
import fornari.nucleo.domain.entity.Sala;
import fornari.nucleo.domain.entity.Usuario;
import fornari.nucleo.domain.mapper.EventoMapper;
import fornari.nucleo.service.AlunoService;
import fornari.nucleo.service.EventoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários do EventoController")

class EventoControllerTest {

    @Mock
    private EventoService eventoService;

    @InjectMocks
    private EventoController eventoController;

//    @Test
//    @DisplayName("Deve criar um novo evento com sucesso")
//    void criarEvento() {
//        EventoCriacaoReqDto eventoCriacaoReqDto = EventoCriacaoReqDto.builder()
//                .usuarioId(1)
//                .salas(Collections.emptyList())
//                .titulo("teste")
//                .descricao("descricao teste")
//                .build();
//
//        when(eventoService.criar(EventoMapper.toEvento(eventoCriacaoReqDto),eventoCriacaoReqDto.getUsuarioId(),eventoCriacaoReqDto.getSalas())).thenReturn(EventoMapper.toEvento(eventoCriacaoReqDto));
//
//        ResponseEntity<EventoRespostaDto> resposta = eventoController.criar(eventoCriacaoReqDto);
//
//        assertNotNull(resposta.getBody());
//        assertEquals("teste", resposta.getBody().getTitulo());
//        assertEquals("descricao teste", resposta.getBody().getDescricao());
//
//    }

    @Test
    @DisplayName("Deve listar corretamente os eventos")
    void listarCorretamente() {
        // Arrange
        Evento evento1 = new Evento(); // Crie um evento com os dados necessários
        evento1.setTitulo("Evento 1");
        evento1.setDescricao("Descrição do evento 1");

        Evento evento2 = new Evento(); // Crie outro evento
        evento2.setTitulo("Evento 2");
        evento2.setDescricao("Descrição do evento 2");

        // Configura o mock do serviço para retornar a lista de eventos
        when(eventoService.listar()).thenReturn(List.of(evento1, evento2));

        // Act
        ResponseEntity<List<EventoRespostaDto>> resposta = eventoController.listar();

        // Assert
        assertNotNull(resposta.getBody()); // Verifica se a resposta não é nula
        assertEquals(2, resposta.getBody().size()); // Verifica se a lista de eventos tem 2 itens
        assertEquals("Evento 1", resposta.getBody().get(0).getTitulo()); // Verifica o título do primeiro evento
        assertEquals("Descrição do evento 1", resposta.getBody().get(0).getDescricao()); // Verifica a descrição do primeiro evento
        assertEquals("Evento 2", resposta.getBody().get(1).getTitulo()); // Verifica o título do segundo evento
        assertEquals("Descrição do evento 2", resposta.getBody().get(1).getDescricao()); // Verifica a descrição do segundo evento
    }

    @Test
    @DisplayName("Deve listar corretamente os eventos por ID da sala")
    void listarPorSala() {
        // Criação de eventos simulados associados à salaId
        Evento evento1 = new Evento();
        evento1.setTitulo("Evento 1");
        evento1.setDescricao("Descrição do evento 1");

        Evento evento2 = new Evento();
        evento2.setTitulo("Evento 2");
        evento2.setDescricao("Descrição do evento 2");

        List<Evento> eventos = new ArrayList<Evento>();
        eventos.add(evento1);
        eventos.add(evento2);

        Sala sala1 = new Sala();
        sala1.setNome("Sala 1");
        sala1.setId(1);
        sala1.setEventos(eventos);


        // Configura o mock do serviço para retornar a lista de eventos
        when(eventoService.listarPorSala(sala1.getId())).thenReturn(List.of(evento1, evento2));

        // Act
        ResponseEntity<List<EventoRespostaDto>> resposta = eventoController.listarPorSala(sala1.getId());

        // Assert
        assertNotNull(resposta.getBody()); // Verifica se a resposta não é nula
        assertEquals(2, resposta.getBody().size()); // Verifica se a lista de eventos tem 2 itens
        assertEquals("Evento 1", resposta.getBody().get(0).getTitulo()); // Verifica o título do primeiro evento
        assertEquals("Descrição do evento 1", resposta.getBody().get(0).getDescricao()); // Verifica a descrição do primeiro evento
        assertEquals("Evento 2", resposta.getBody().get(1).getTitulo()); // Verifica o título do segundo evento
        assertEquals("Descrição do evento 2", resposta.getBody().get(1).getDescricao()); // Verifica a descrição do segundo evento
    }

    @Test
    @DisplayName("Deve listar corretamente eventos do tipo PUBLICACAO")
    void listarPublicacoes() {
        // Arrange
        Evento evento1 = new Evento();
        evento1.setTitulo("Publicação 1");
        evento1.setDescricao("Descrição da publicação 1");
        evento1.setTipo("PUBLICACAO"); // Garantir que o tipo é "PUBLICACAO"

        Evento evento2 = new Evento();
        evento2.setTitulo("Publicação 2");
        evento2.setDescricao("Descrição da publicação 2");
        evento2.setTipo("PUBLICACAO"); // Garantir que o tipo é "PUBLICACAO"

        // Configura o mock do serviço para retornar a lista de publicações
        when(eventoService.listarPublicacoes()).thenReturn(List.of(evento1, evento2));

        // Act
        ResponseEntity<List<EventoRespostaDto>> resposta = eventoController.listarPublicacoes();

        // Assert
        assertNotNull(resposta.getBody()); // Verifica se a resposta não é nula
        assertEquals(2, resposta.getBody().size()); // Verifica se a lista de publicações tem 2 itens
        assertEquals("Publicação 1", resposta.getBody().get(0).getTitulo()); // Verifica o título da primeira publicação
        assertEquals("Descrição da publicação 1", resposta.getBody().get(0).getDescricao()); // Verifica a descrição da primeira publicação
        assertEquals("Publicação 2", resposta.getBody().get(1).getTitulo()); // Verifica o título da segunda publicação
        assertEquals("Descrição da publicação 2", resposta.getBody().get(1).getDescricao()); // Verifica a descrição da segunda publicação
    }

    @Test
    @DisplayName("Deve listar corretamente as publicações de um usuário")
    void listPublicationsByUser() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNome("Felipe");
        usuario.setEmail("felipe@gamil.com");
        usuario.setTelefone("11999999999");
        usuario.setEndereco(new Endereco());
        // Criação de eventos simulados associados ao usuarioId
        Evento evento1 = new Evento();
        evento1.setTitulo("Publicação 1");
        evento1.setDescricao("Descrição da publicação 1");
        evento1.setUsuario(usuario); // Associando ao ID do usuário

        Evento evento2 = new Evento();
        evento2.setTitulo("Publicação 2");
        evento2.setDescricao("Descrição da publicação 2");
        evento2.setUsuario(usuario); // Associando ao ID do usuário

        // Configura o mock do serviço para retornar a lista de publicações
        when(eventoService.findByUserId(usuario.getId())).thenReturn(List.of(evento1, evento2));

        // Act
        ResponseEntity<List<EventoRespostaDto>> resposta = eventoController.listPublicationsByUser(usuario.getId());

        // Assert
        assertNotNull(resposta.getBody()); // Verifica se a resposta não é nula
        assertEquals(2, resposta.getBody().size()); // Verifica se a lista de publicações tem 2 itens
        assertEquals("Publicação 1", resposta.getBody().get(0).getTitulo()); // Verifica o título da primeira publicação
        assertEquals("Descrição da publicação 1", resposta.getBody().get(0).getDescricao()); // Verifica a descrição da primeira publicação
        assertEquals("Publicação 2", resposta.getBody().get(1).getTitulo()); // Verifica o título da segunda publicação
        assertEquals("Descrição da publicação 2", resposta.getBody().get(1).getDescricao()); // Verifica a descrição da segunda publicação
    }

    @Test
    @DisplayName("Deve retornar No Content quando o usuário não tiver publicações")
    void listPublicationsByUserSemPublicacoes() {
        // Arrange
        Integer usuarioId = 1; // ID do usuário para o qual vamos buscar as publicações

        // Configura o mock do serviço para retornar uma lista vazia
        when(eventoService.findByUserId(usuarioId)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<EventoRespostaDto>> resposta = eventoController.listPublicationsByUser(usuarioId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode()); // Verifica se o status é 204 No Content
        assertNull(resposta.getBody()); // Verifica se o corpo da resposta é nulo (sem conteúdo)
    }

    @Test
    @DisplayName("Deve adicionar publicações à sala corretamente")
    void enrollPublicationWithClassroom() {
        // Arrange
        Evento evento = new Evento();
        evento.setId(1);
        evento.setTitulo("Titulo teste");
        evento.setDescricao("Descricao teste");

        List<Integer> salasIds = Arrays.asList(2, 3); // IDs das salas que serão associadas ao evento
        Evento eventoMock = new Evento();
        eventoMock.setId(1);
        eventoMock.setTitulo("Titulo retorno");
        eventoMock.setDescricao("Descricao retorno");

        // Mock do serviço para retornar o evento e associar salas
        when(eventoService.enrollPublicationWithClassroom(eq(null), eq(evento.getId()), eq(salasIds)))
                .thenReturn(eventoMock);

        // Act
        ResponseEntity<EventoRespostaDto> resposta = eventoController.enrollPublicationWithClassroom(evento.getId(), salasIds);

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
        assertEquals("Titulo retorno", resposta.getBody().getTitulo());
        assertEquals("Descricao retorno", resposta.getBody().getDescricao());
    }

    @Test
    @DisplayName("Deve excluir um evento corretamente")
    void deleteEvento() {
        // Arrange
        Integer eventoId = 1;

        // Mock do serviço para realizar a exclusão
        doNothing().when(eventoService).delete(eventoId);

        // Act
        ResponseEntity<Void> resposta = eventoController.delete(eventoId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
        verify(eventoService, times(1)).delete(eventoId);  // Verifica se o serviço foi chamado uma vez
    }

    @Test
    @DisplayName("Deve atualizar um evento corretamente")
    void updateEvento() {
        // Arrange
        Integer eventoId = 1;
        EventoCriacaoReqDto eventoUpdateReqDto = EventoCriacaoReqDto.builder()
                .titulo("Titulo update")
                .descricao("Descricao update")
                .data(LocalDateTime.now())
                .salas(Arrays.asList(1, 2))
                .build();

        // Criando o mock de evento para ser retornado pelo serviço
        Evento eventoMock = new Evento();
        eventoMock.setTitulo(eventoUpdateReqDto.getTitulo());
        eventoMock.setDescricao(eventoUpdateReqDto.getDescricao());

        // Mock do serviço para retornar o evento atualizado
        when(eventoService.update(eq(eventoId), any(Evento.class), eq(eventoUpdateReqDto.getSalas())))
                .thenReturn(eventoMock);

        // Act
        ResponseEntity<EventoRespostaDto> resposta = eventoController.update(eventoId, eventoUpdateReqDto);

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
        assertEquals(eventoUpdateReqDto.getTitulo(), resposta.getBody().getTitulo());
        assertEquals(eventoUpdateReqDto.getDescricao(), resposta.getBody().getDescricao());
    }













}
