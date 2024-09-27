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
