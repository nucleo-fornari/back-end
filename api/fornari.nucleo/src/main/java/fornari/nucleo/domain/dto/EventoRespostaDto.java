package fornari.nucleo.domain.dto;

import fornari.nucleo.domain.dto.sala.SalaResponseDto;
import fornari.nucleo.domain.dto.usuario.UsuarioResponseDto;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class EventoRespostaDto {
    private Integer id;
    private String titulo;
    private String descricao;
    private LocalDateTime data;
    private String local;
    private String tipo;
    private UsuarioResponseDto responsavel;
    private List<SalaResponseDto> salas;
    private Boolean encerrado;
}
