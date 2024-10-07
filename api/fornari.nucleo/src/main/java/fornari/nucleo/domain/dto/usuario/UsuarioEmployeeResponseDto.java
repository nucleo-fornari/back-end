package fornari.nucleo.domain.dto.usuario;

import fornari.nucleo.domain.dto.EnderecoDto;
import fornari.nucleo.domain.entity.Endereco;
import lombok.Data;

import java.sql.Date;

@Data
public class UsuarioEmployeeResponseDto {

    private Integer id;

    private String nome;

    private String cpf;

    private String email;

    private Date dtNasc;

    private String funcao;

    private EnderecoDto endereco;
}
