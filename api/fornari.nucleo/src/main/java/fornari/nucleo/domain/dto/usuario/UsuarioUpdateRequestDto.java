package fornari.nucleo.domain.dto.usuario;

import fornari.nucleo.domain.dto.EnderecoDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UsuarioUpdateRequestDto {
    @NotNull
    @NotEmpty
    private String nome;

    private LocalDate dtNasc;

    @NotNull
    @NotEmpty
    private String funcao;

    private EnderecoDto endereco;
}
