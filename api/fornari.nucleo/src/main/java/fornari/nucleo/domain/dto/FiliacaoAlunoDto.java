package fornari.nucleo.domain.dto;

import fornari.nucleo.domain.dto.usuario.responsavel.ResponsavelAlunoDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FiliacaoAlunoDto {

    private ResponsavelAlunoDto responsavel;

    @NotBlank
    @NotNull
    @NotEmpty
    private String parentesco;
}
