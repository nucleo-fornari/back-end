package fornari.nucleo.domain.mapper;

import fornari.nucleo.domain.dto.UsuarioDto;
import fornari.nucleo.domain.entity.Endereco;
import fornari.nucleo.domain.entity.Usuario;
import fornari.nucleo.helper.Generator;
import fornari.nucleo.helper.messages.ConstMessages;
import fornari.nucleo.helper.validation.GenericValidations;
import jakarta.validation.ValidationException;

import java.util.ArrayList;

public class UsuarioMapper {
    public static Usuario toUser(UsuarioDto userDto) {
        if (userDto == null) {
            return null;
        }

        Usuario user = new Usuario();
        user.setNome(userDto.getNome());
        user.setCpf(userDto.getCpf());
        user.setEmail(userDto.getEmail());
        user.setDtNasc(userDto.getDtNasc());
        user.setFuncao(userDto.getFuncao());
        user.setSenha(userDto.getSenha());
        user.getEndereco().setCep(userDto.getEndereco().getCep());
                user.getEndereco().setComplemento(userDto.getEndereco().getComplemento());
                user.getEndereco().setBairro(userDto.getEndereco().getBairro());
                user.getEndereco().setLocalidade(userDto.getEndereco().getLocalidade());
                user.getEndereco().setUf(userDto.getEndereco().getUf());
                user.getEndereco().setLogradouro(userDto.getEndereco().getLogradouro());
                user.getEndereco().setNumero(userDto.getEndereco().getNumero());

                return user;
    }

    public static UsuarioDto toDTO(Usuario user) {
        if (user == null) {
            return null;
        }

        UsuarioDto dto = new UsuarioDto();
        dto.setId(user.getId());
        dto.setNome(user.getNome());
        dto.setCpf(user.getCpf());
        dto.setEmail(user.getEmail());
        dto.setEndereco(user.getEndereco());
        dto.setFuncao(user.getFuncao());
        dto.setDtNasc(user.getDtNasc());
        dto.getEndereco().setUsuarios(new ArrayList<>());
        dto.setSenha(user.getSenha());

        return dto;
    }
}
