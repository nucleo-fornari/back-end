package fornari.nucleo.domain.dto.auth;

import fornari.nucleo.helper.messages.ConstMessages;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class LoginRequestDto {
    private String id;
    private String cpf;
    @Email(message = ConstMessages.INVALID_EMAIL)
    private String email;
}
