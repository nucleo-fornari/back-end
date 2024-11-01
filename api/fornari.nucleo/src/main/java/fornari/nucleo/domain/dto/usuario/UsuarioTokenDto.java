package fornari.nucleo.domain.dto.usuario;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioTokenDto {
    private Integer userId;
    private String nome;
    private String email;
    private String token;
}
