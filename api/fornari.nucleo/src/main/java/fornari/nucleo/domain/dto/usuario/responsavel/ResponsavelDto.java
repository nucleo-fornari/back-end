package fornari.nucleo.domain.dto.usuario.responsavel;

import fornari.nucleo.domain.dto.EnderecoDto;
import fornari.nucleo.domain.entity.Endereco;
import lombok.Data;

import java.sql.Date;

@Data
public class ResponsavelDto {

    private Integer id;

    private String nome;

    private String cpf;

    private String telefone;

    private String email;

    private String senha;

    private Date dtNasc;

    private String funcao;

    private EnderecoDto endereco;


}
