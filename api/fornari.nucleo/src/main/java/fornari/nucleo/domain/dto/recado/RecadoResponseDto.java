package fornari.nucleo.domain.dto.recado;

import fornari.nucleo.domain.dto.usuario.UsuarioResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecadoResponseDto {

    private Integer id;
    private String titulo;
    private String conteudo;
    private UsuarioResponseDto responsavel;
    private String alunoNome;
    private LocalDateTime dtCriacao;
}
