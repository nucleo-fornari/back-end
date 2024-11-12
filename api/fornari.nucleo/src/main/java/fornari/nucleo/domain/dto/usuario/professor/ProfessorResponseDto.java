package fornari.nucleo.domain.dto.usuario.professor;

import fornari.nucleo.domain.dto.EnderecoDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ProfessorResponseDto {

    private int id;
    private String nome;
    private String cpf;
    private String email;
    private LocalDate dtNasc;
    private String funcao;
    private EnderecoDto endereco;
    private SalaProfessorDto sala;

    @Data
    @Builder
    public static class SalaProfessorDto {
        private int id;
        private String localizacao;
        private String nome;
    }
}
