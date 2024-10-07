package fornari.nucleo.domain.mapper;

import fornari.nucleo.domain.dto.EnderecoDto;
import fornari.nucleo.domain.dto.usuario.UsuarioEmployeeResponseDto;
import fornari.nucleo.domain.dto.usuario.UsuarioWithoutPasswordDto;
import fornari.nucleo.domain.entity.Endereco;
import fornari.nucleo.domain.entity.Usuario;

public class UsuarioMapper {
    public static Usuario toUser(UsuarioEmployeeResponseDto userDto) {
        if (userDto == null) {
            return null;
        }

        Usuario user = new Usuario();
        user.setNome(userDto.getNome());
        user.setCpf(userDto.getCpf());
        user.setEmail(userDto.getEmail());
        user.setDtNasc(userDto.getDtNasc());
        user.setFuncao(userDto.getFuncao());
        user.setEndereco(new Endereco());
        user.getEndereco().setId(userDto.getEndereco().getId());
        user.getEndereco().setCep(userDto.getEndereco().getCep());
        user.getEndereco().setComplemento(userDto.getEndereco().getComplemento());
        user.getEndereco().setBairro(userDto.getEndereco().getBairro());
        user.getEndereco().setLocalidade(userDto.getEndereco().getLocalidade());
        user.getEndereco().setUf(userDto.getEndereco().getUf());
        user.getEndereco().setLogradouro(userDto.getEndereco().getLogradouro());
        user.getEndereco().setNumero(userDto.getEndereco().getNumero());

        return user;
    }

    public static UsuarioEmployeeResponseDto toDTO(Usuario user) {
        if (user == null) {
            return null;
        }

        UsuarioEmployeeResponseDto dto = new UsuarioEmployeeResponseDto();
        dto.setId(user.getId());
        dto.setNome(user.getNome());
        dto.setCpf(user.getCpf());
        dto.setEmail(user.getEmail());
        dto.setEndereco(EnderecoMapper.toEnderecoDto(user.getEndereco()));
        dto.setFuncao(user.getFuncao());
        dto.setDtNasc(user.getDtNasc());

        return dto;
    }

    public static UsuarioWithoutPasswordDto toDto(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        UsuarioWithoutPasswordDto dto = new UsuarioWithoutPasswordDto();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setCpf(usuario.getCpf());
        dto.setEmail(usuario.getEmail());
        dto.setToken(usuario.getToken());
        dto.setDtNasc(usuario.getDtNasc());
        dto.setFuncao(usuario.getFuncao());
        dto.setEndereco(EnderecoMapper.toEnderecoDto(usuario.getEndereco()));

        return dto;
    }
}
