package fornari.nucleo.domain.dto;

import lombok.Data;

@Data
public class EnderecoDto {

    private Integer id;
    private String cep;
    private String uf;
    private String localidade;
    private String bairro;
    private String logradouro;
    private String complemento;
    private Integer numero;
}
