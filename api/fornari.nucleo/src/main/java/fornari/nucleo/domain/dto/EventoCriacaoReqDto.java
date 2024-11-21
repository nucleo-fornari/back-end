package fornari.nucleo.domain.dto;

import fornari.nucleo.helper.messages.ConstMessages;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class EventoCriacaoReqDto {

    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;

    @FutureOrPresent
    private LocalDateTime data;

    @NotBlank
    private String tipo;

    @NotNull
    private Integer usuarioId;

    private List<Integer> salas;
}
