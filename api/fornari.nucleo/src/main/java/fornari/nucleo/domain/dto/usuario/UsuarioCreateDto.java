package fornari.nucleo.domain.dto.usuario;

import fornari.nucleo.domain.dto.EnderecoDto;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioCreateDto {

    @NotBlank
    private String nome;

    @NotBlank
    private String cpf;

    @NotBlank
    private String telefone;

    @Email
    private String email;

    @Past
    private LocalDate dtNasc;

    @NotBlank
    private String funcao;

    @NotNull
    private EnderecoDto endereco;
}