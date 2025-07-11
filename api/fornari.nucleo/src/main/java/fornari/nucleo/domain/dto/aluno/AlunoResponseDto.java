package fornari.nucleo.domain.dto.aluno;

import fornari.nucleo.domain.dto.AlunoAvaliacao.AlunoAvaliacaoDto;
import fornari.nucleo.domain.dto.FiliacaoAlunoDto;
import fornari.nucleo.domain.dto.recado.RecadoResponseDto;
import fornari.nucleo.domain.dto.restricao.RestricaoResponseWithoutAlunosDto;
import fornari.nucleo.domain.dto.sala.SalaResponseDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class AlunoResponseDto {

    private Integer id;

    private String ra;

    private String nome;

    private boolean laudado;

    private String laudoNome;

    private LocalDate dtNasc;

    private List<FiliacaoAlunoDto> filiacoes;

    private String observacoes;

    private List<RestricaoResponseWithoutAlunosDto> restricoes;

    private SalaResponseDto sala;

    private List<RecadoResponseDto> recados;

    private List<AlunoAvaliacaoDto> avaliacoes;
}
