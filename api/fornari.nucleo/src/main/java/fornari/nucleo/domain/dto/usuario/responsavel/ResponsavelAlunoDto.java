package fornari.nucleo.domain.dto.usuario.responsavel;

import fornari.nucleo.domain.dto.EnderecoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Data
@Builder
public class ResponsavelAlunoDto {

    private Integer id;

    private String nome;

    private String cpf;

    private String telefone;

    private String email;

    private LocalDate dtNasc;

    private String funcao;

    private EnderecoDto endereco;

    public ResponsavelAlunoDto(Integer id, String nome, String cpf, String telefone, String email, LocalDate dtNasc, String funcao, EnderecoDto endereco) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.dtNasc = dtNasc;
        this.funcao = funcao;
        this.endereco = endereco;
    }

    public ResponsavelAlunoDto() {}
}
