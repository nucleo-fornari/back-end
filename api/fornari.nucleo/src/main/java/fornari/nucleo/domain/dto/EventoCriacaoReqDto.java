package fornari.nucleo.domain.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

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
    private String local;
    @NotBlank
    private String tipo;
    @NotNull
    private Integer usuarioId;
    private Boolean encerrado;
}
