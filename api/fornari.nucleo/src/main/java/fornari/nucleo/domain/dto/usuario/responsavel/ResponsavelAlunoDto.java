package fornari.nucleo.domain.dto.usuario.responsavel;

import fornari.nucleo.domain.dto.EnderecoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class ResponsavelAlunoDto {

    private Integer id;

    private String nome;

    private String cpf;

    private String email;

    private LocalDate dtNasc;

    private String funcao;

    private EnderecoDto endereco;

}
