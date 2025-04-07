package fornari.nucleo.domain.dto.usuario;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioLoginDto {

    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 30)
    private String senha;
}
