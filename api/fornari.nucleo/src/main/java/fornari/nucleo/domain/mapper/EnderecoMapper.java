package fornari.nucleo.domain.mapper;

import fornari.nucleo.domain.dto.EnderecoApiExternaDto;
import fornari.nucleo.domain.dto.EnderecoDto;
import fornari.nucleo.domain.entity.Endereco;

public class EnderecoMapper {

    public static EnderecoDto toEnderecoDto(EnderecoApiExternaDto enderecoApiExternaDto) {
        EnderecoDto enderecoDto = new EnderecoDto();
        enderecoDto.setBairro(enderecoApiExternaDto.getBairro());
        enderecoDto.setCep(enderecoApiExternaDto.getCep());
        enderecoDto.setLocalidade(enderecoApiExternaDto.getLocalidade());
        enderecoDto.setUf(enderecoApiExternaDto.getUf());
        enderecoDto.setLogradouro(enderecoApiExternaDto.getLogradouro());

        return enderecoDto;
    }

    public static EnderecoDto toEnderecoDto(Endereco endereco) {
        EnderecoDto enderecoDto = new EnderecoDto();
        enderecoDto.setId(endereco.getId());
        enderecoDto.setBairro(endereco.getBairro());
        enderecoDto.setCep(endereco.getCep());
        enderecoDto.setLocalidade(endereco.getLocalidade());
        enderecoDto.setComplemento(endereco.getComplemento());
        enderecoDto.setUf(endereco.getUf());
        enderecoDto.setLogradouro(endereco.getLogradouro());
        enderecoDto.setNumero(endereco.getNumero());

        return enderecoDto;
    }

    public static Endereco toEndereco(EnderecoDto enderecoDto) {
        if (enderecoDto == null) return null;

        Endereco endereco = new Endereco();
        endereco.setBairro(enderecoDto.getBairro());
        endereco.setCep(enderecoDto.getCep());
        endereco.setLocalidade(enderecoDto.getLocalidade());
        endereco.setUf(enderecoDto.getUf());
        endereco.setLogradouro(enderecoDto.getLogradouro());
        return endereco;
    }
}
