package fornari.nucleo.domain.dto.sala;

import fornari.nucleo.domain.dto.aluno.AlunoResponseDto;
import fornari.nucleo.domain.dto.sala.grupo.SalaGrupoResponseDto;
import fornari.nucleo.domain.dto.usuario.professor.ProfessorResponseDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SalaResponseDto {
    private Integer id;
    private String localizacao;
    private String nome;
    private List<ProfessorResponseDto> professores;
    private List<AlunoResponseDto> alunos;
    private SalaGrupoResponseDto grupo;
}
