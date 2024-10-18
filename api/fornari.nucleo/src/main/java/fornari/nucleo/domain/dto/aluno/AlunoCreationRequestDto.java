package fornari.nucleo.domain.dto.aluno;

import fornari.nucleo.domain.dto.FiliacaoAlunoDto;
import lombok.Builder;
import lombok.Data;
import org.aspectj.lang.annotation.After;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class AlunoCreationRequestDto {

    private Integer id;

    private String ra;

    private String nome;

    private boolean laudado;

    private LocalDate dtNasc;

    private String observacoes;

    private FiliacaoAlunoDto filiacao;

    private List<Integer> restricoes;
}
