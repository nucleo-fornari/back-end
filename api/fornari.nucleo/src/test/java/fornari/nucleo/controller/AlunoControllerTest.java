package fornari.nucleo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fornari.nucleo.domain.dto.EnderecoDto;
import fornari.nucleo.domain.dto.FiliacaoAlunoDto;
import fornari.nucleo.domain.dto.aluno.AlunoRequestDto;
import fornari.nucleo.domain.dto.aluno.AlunoResponseDto;
import fornari.nucleo.domain.dto.usuario.responsavel.ResponsavelAlunoDto;
import fornari.nucleo.domain.entity.Aluno;
import fornari.nucleo.domain.entity.Usuario;
import fornari.nucleo.service.AlunoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários do AlunoController")
class AlunoControllerTest {

    @Mock
    private AlunoService service;

    @InjectMocks
    private AlunoController controller;

//    @Test
//    @DisplayName("Deve criar um novo aluno com sucesso")
//    void createDeveCriarNovoAluno() {
//        // Arrange
//        FiliacaoAlunoDto filiacao = new FiliacaoAlunoDto();
//        ResponsavelAlunoDto responsavel = new ResponsavelAlunoDto();
//        responsavel.setId(1);
//        responsavel.setNome("Caique de Andrade Lucio");
//        responsavel.setCpf("48585921897");
//        responsavel.setTelefone("11983987068");
//        responsavel.setEmail("caique@gmail.com");
//        filiacao.setResponsavel(responsavel);
//        filiacao.setParentesco("GENITOR");
//
//        AlunoRequestDto requestDto = new AlunoRequestDto();
//        requestDto.setNome("João");
//        requestDto.setFiliacao(filiacao);
//
//        Aluno aluno = new Aluno();
//        aluno.setId(1);
//        aluno.setNome("João");
//        aluno.setFiliacoes(Collections.emptyList());
//
//        when(service.create(any(Aluno.class), any(Usuario.class), anyString(), anyList(), any(MultipartFile.class))).thenReturn(aluno);
//
//        // Act
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            String body = objectMapper.writeValueAsString(requestDto);
//            System.out.println(body);
//            ResponseEntity<AlunoResponseDto> resultado = controller.create(body, null);
//            // Assert
//            assertEquals(HttpStatus.CREATED, resultado.getStatusCode());
//            assertNotNull(resultado.getBody());
//            assertEquals("João", resultado.getBody().getNome());
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//    }



    @Test
    @DisplayName("Deve listar todos os alunos")
    void listDeveRetornarListaDeAlunos() {
        Aluno aluno = new Aluno();
        aluno.setId(1);
        aluno.setNome("João");

        when(service.findAll()).thenReturn(List.of(aluno));

        ResponseEntity<List<AlunoResponseDto>> resultado = controller.list();

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertNotNull(resultado.getBody());
        assertFalse(resultado.getBody().isEmpty());
        assertEquals("João", resultado.getBody().get(0).getNome());
    }

    @Test
    @DisplayName("Deve listar todos os alunos sem sala")
    void listAlunosSemSalaDeveRetornarLista() {
        Aluno aluno = new Aluno();
        aluno.setId(2);
        aluno.setNome("Maria");

        when(service.getSemSala()).thenReturn(List.of(aluno));

        ResponseEntity<List<AlunoResponseDto>> resultado = controller.listAlunosSemSala();

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertNotNull(resultado.getBody());
        assertFalse(resultado.getBody().isEmpty());
        assertEquals("Maria", resultado.getBody().get(0).getNome());
    }

    @Test
    @DisplayName("Deve buscar aluno pelo ID")
    void getByIdDeveRetornarAluno() {
        Aluno aluno = new Aluno();
        aluno.setId(1);
        aluno.setNome("João");

        when(service.findById(1)).thenReturn(aluno);

        ResponseEntity<AlunoResponseDto> resultado = controller.getById(1);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertNotNull(resultado.getBody());
        assertEquals("João", resultado.getBody().getNome());
    }

//    @Test
//    @DisplayName("Deve adicionar um responsável ao aluno")
//    void addResponsavelDeveAdicionarComSucesso() {
//
//        ResponsavelAlunoDto responsavel = new ResponsavelAlunoDto();
//        responsavel.setId(null);
//        responsavel.setNome("nome_09de73f3da6c");
//        responsavel.setCpf("48585921897");
//        responsavel.setEmail("caique@gmail.com");
//        responsavel.setDtNasc(LocalDate.of(2024, 10, 20));
//        responsavel.setFuncao("RESPONSAVEL");
//
//        EnderecoDto endereco = new EnderecoDto();
//        endereco.setId(null);
//        endereco.setCep("cep_ea2a81bd54dc");
//        endereco.setUf("SP");
//        endereco.setLocalidade("localidade_1e08dc954049");
//        endereco.setBairro("bairro_5d2ecce26e94");
//        endereco.setLogradouro("logradouro_50f9f24642c3");
//        endereco.setComplemento("complemento_d92d029f6bbc");
//        endereco.setNumero("0");
//
//        responsavel.setEndereco(endereco);
//
//        FiliacaoAlunoDto filiacao = new FiliacaoAlunoDto(responsavel, "GENITOR");
//        Aluno aluno = new Aluno();
//        aluno.setId(1);
//        aluno.setNome("João");
//
//        when(service.addResponsavel(any(), eq(1), eq("Mãe"))).thenReturn(aluno);
//
//        ResponseEntity<AlunoResponseDto> resultado = controller.addResponsavel(1, filiacao);
//
//        assertEquals(HttpStatus.OK, resultado.getStatusCode());
//        assertNotNull(resultado.getBody());
//        assertEquals("João", resultado.getBody().getNome());
//    }

//    @Test
//    @DisplayName("Deve atualizar aluno pelo ID")
//    void updateAlunoDeveAtualizarAluno() {
//        AlunoRequestDto requestDto = new AlunoRequestDto();
//        requestDto.setNome("João Atualizado");
//
//        Aluno aluno = new Aluno();
//        aluno.setId(1);
//        aluno.setNome("João");
//
//        when(service.update(any(Aluno.class), anyList(), eq(1))).thenReturn(aluno);
//
//        ResponseEntity<AlunoResponseDto> resultado = controller.updateAluno(1, requestDto);
//
//        assertEquals(HttpStatus.OK, resultado.getStatusCode());
//        assertNotNull(resultado.getBody());
//        assertEquals("João Atualizado", resultado.getBody().getNome());
//    }


    @Test
    @DisplayName("Deve excluir aluno pelo ID")
    void deleteDeveExcluirAluno() {
        ResponseEntity<Void> resultado = controller.delete(1);

        assertEquals(HttpStatus.NO_CONTENT, resultado.getStatusCode());
        verify(service, times(1)).delete(1);
    }

    @Test
    @DisplayName("Deve excluir responsável de aluno")
    void deleteResponsavelDeveExcluir() {
        ResponseEntity<AlunoResponseDto> resultado = controller.delete(1, 2);

        assertEquals(HttpStatus.NO_CONTENT, resultado.getStatusCode());
        verify(service, times(1)).delete(1, 2);
    }

    @Test
    @DisplayName("Deve matricular aluno em sala")
    void enrollStudentWithClassroomDeveMatricular() {
        Aluno aluno = new Aluno();
        aluno.setId(1);
        aluno.setNome("João");

        when(service.enrollStudentWithClassroom(1, 101)).thenReturn(aluno);

        ResponseEntity<AlunoResponseDto> resultado = controller.enrollStudentWithClassroom(1, 101);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertNotNull(resultado.getBody());
        assertEquals("João", resultado.getBody().getNome());
    }

    @Test
    @DisplayName("Deve remover aluno de sala")
    void removeStudentFromClassroomDeveRemover() {
        Aluno aluno = new Aluno();
        aluno.setId(1);
        aluno.setNome("João");

        when(service.removeStudentFromClassroom(1)).thenReturn(aluno);

        ResponseEntity<AlunoResponseDto> resultado = controller.removeStudentFromClassroom(1);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertNotNull(resultado.getBody());
        assertEquals("João", resultado.getBody().getNome());
    }
}