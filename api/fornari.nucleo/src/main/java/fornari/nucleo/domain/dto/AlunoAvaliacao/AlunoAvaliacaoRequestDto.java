package fornari.nucleo.domain.dto.AlunoAvaliacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlunoAvaliacaoRequestDto {

    private String periodo;

    private String bimestre;

    private String textoSocioAfetivaEmocional;

    private String textoFisicoMotora;

    private String ano;

    private String textoCognitiva;

    private Integer alunoId;
}
