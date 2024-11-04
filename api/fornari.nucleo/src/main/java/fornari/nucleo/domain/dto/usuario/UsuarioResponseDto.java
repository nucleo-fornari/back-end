package fornari.nucleo.domain.dto.usuario;

import fornari.nucleo.domain.dto.EnderecoDto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioResponseDto {

    private int id;
    private String nome;
    private String cpf;
    private String email;
    private LocalDate dtNasc;
    private String funcao;
    private EnderecoDto endereco;
}
