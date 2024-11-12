package fornari.nucleo.domain.dto;

import fornari.nucleo.domain.dto.sala.SalaResponseDto;
import fornari.nucleo.domain.entity.Endereco;
import fornari.nucleo.domain.entity.Usuario;
import fornari.nucleo.helper.messages.ConstMessages;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
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
    private List<SalaResponseDto> salas;
    private Boolean encerrado;
}
