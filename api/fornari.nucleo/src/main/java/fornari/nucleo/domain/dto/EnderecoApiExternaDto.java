package fornari.nucleo.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnderecoApiExternaDto {
    private String cep;
    private String uf;
    private String localidade;
    private String bairro;
    private String logradouro;
}
