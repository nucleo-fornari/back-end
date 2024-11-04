package fornari.nucleo.domain.dto;

import fornari.nucleo.domain.dto.usuario.responsavel.ResponsavelAlunoDto;
import fornari.nucleo.helper.messages.ConstMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiliacaoAlunoDto {

    private ResponsavelAlunoDto responsavel;

    @NotBlank
    @NotNull
    @NotEmpty
    private String parentesco;

    public FiliacaoAlunoDto (ResponsavelAlunoDto responsavelAlunoDto, String parentesco) {
        this.responsavel = responsavelAlunoDto;
        this.parentesco = parentesco;
    }
}
