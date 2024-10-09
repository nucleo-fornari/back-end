package fornari.nucleo.domain.dto;

import fornari.nucleo.domain.dto.usuario.responsavel.ResponsavelAlunoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiliacaoAlunoDto {

    private ResponsavelAlunoDto responsavel;

    private String parentesco;

    public FiliacaoAlunoDto (ResponsavelAlunoDto responsavelAlunoDto, String parentesco) {
        this.responsavel = responsavelAlunoDto;
        this.parentesco = parentesco;
    }

}
