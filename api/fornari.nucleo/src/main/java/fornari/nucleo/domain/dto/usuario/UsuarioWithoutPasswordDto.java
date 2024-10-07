package fornari.nucleo.domain.dto.usuario;

import fornari.nucleo.domain.dto.EnderecoDto;
import lombok.Data;

import java.sql.Date;

@Data
public class UsuarioWithoutPasswordDto {

    private Integer id;

    private String nome;

    private String cpf;

    private String email;

    private String token;

    private Date dtNasc;

    private String funcao;

    private EnderecoDto endereco;
}
