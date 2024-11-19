package fornari.nucleo.domain.dto.usuario;

import fornari.nucleo.domain.dto.EnderecoDto;
import fornari.nucleo.domain.dto.FiliacaoAlunoDto;
import fornari.nucleo.domain.dto.aluno.AlunoResponseDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UsuarioResponseDto {

    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private LocalDate dtNasc;
    private String funcao;
    private List<AlunoResponseDto> afiliados;
    private EnderecoDto endereco;
}
