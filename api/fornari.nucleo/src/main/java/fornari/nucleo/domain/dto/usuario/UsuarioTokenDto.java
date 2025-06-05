package fornari.nucleo.domain.dto.usuario;

import fornari.nucleo.domain.entity.Sala;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioTokenDto {
    private Integer userId;
    private String nome;
    private String email;
    private String funcao;
    private String telefone;
    private boolean lgpd;
    private Integer salaId;
    private String token;
}
