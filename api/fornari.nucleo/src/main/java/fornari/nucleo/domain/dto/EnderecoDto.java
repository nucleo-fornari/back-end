package fornari.nucleo.domain.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EnderecoDto {

    @NotNull
    private Integer id;

    @NotBlank
    @Size(min = 8, max = 8)
    private String cep;

    @NotNull
    @NotEmpty
    @Size(max = 5)
    private String uf;

    @NotBlank
    private String localidade;

    @NotBlank
    private String bairro;

    @NotBlank
    private String logradouro;

    @NotBlank
    private String complemento;

    @NotNull
    @NotEmpty
    @Size(max = 5)
    private String numero;

    public EnderecoDto() {
    }

    public EnderecoDto(Integer id, String cep, String uf, String localidade, String bairro, String logradouro, String complemento, String numero) {
        this.id = id;
        this.cep = cep;
        this.uf = uf;
        this.localidade = localidade;
        this.bairro = bairro;
        this.logradouro = logradouro;
        this.complemento = complemento;
        this.numero = numero;
    }

    public @NotNull Integer getId() {
        return id;
    }

    public void setId(@NotNull Integer id) {
        this.id = id;
    }

    public @NotBlank @Size(min = 8, max = 8) String getCep() {
        return cep;
    }

    public void setCep(@NotBlank @Size(min = 8, max = 8) String cep) {
        this.cep = cep;
    }

    public @NotNull @NotEmpty @Size(max = 5) String getUf() {
        return uf;
    }

    public void setUf(@NotNull @NotEmpty @Size(max = 5) String uf) {
        this.uf = uf;
    }

    public @NotBlank String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(@NotBlank String localidade) {
        this.localidade = localidade;
    }

    public @NotBlank String getBairro() {
        return bairro;
    }

    public void setBairro(@NotBlank String bairro) {
        this.bairro = bairro;
    }

    public @NotBlank String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(@NotBlank String logradouro) {
        this.logradouro = logradouro;
    }

    public @NotBlank String getComplemento() {
        return complemento;
    }

    public void setComplemento(@NotBlank String complemento) {
        this.complemento = complemento;
    }

    public @NotNull @NotEmpty @Size(max = 5) String getNumero() {
        return numero;
    }

    public void setNumero(@NotNull @NotEmpty @Size(max = 5) String numero) {
        this.numero = numero;
    }
}
