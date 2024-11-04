package fornari.nucleo.domain.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EnderecoDto {

    @NotNull
    private Integer id;

    @NotBlank
    @Size(min = 8, max = 8)
    private String cep;

    @NotNull
    @NotEmpty
    @Size(max = 5)
    private String uf;

    @NotBlank
    private String localidade;

    @NotBlank
    private String bairro;

    @NotBlank
    private String logradouro;

    @NotBlank
    private String complemento;

    @NotNull
    @NotEmpty
    @Size(max = 5)
    private String numero;
}
