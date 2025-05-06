package fornari.nucleo.domain.mapper;

import fornari.nucleo.domain.dto.usuario.UsuarioCreateDto;
import fornari.nucleo.domain.dto.usuario.UsuarioResponseDto;
import fornari.nucleo.domain.dto.usuario.UsuarioTokenDto;
import fornari.nucleo.domain.dto.usuario.UsuarioUpdateRequestDto;
import fornari.nucleo.domain.dto.usuario.responsavel.ResponsavelAlunoDto;
import fornari.nucleo.domain.entity.Endereco;
import fornari.nucleo.domain.entity.Usuario;
import org.yaml.snakeyaml.events.Event;

import java.util.stream.Collectors;

public class UsuarioMapper {
    public static Usuario toUser(UsuarioCreateDto userDto) {
        if (userDto == null) {
            return null;
        }

        Usuario user = new Usuario();
        user.setNome(userDto.getNome());
        user.setCpf(userDto.getCpf());
        user.setTelefone(userDto.getTelefone());
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

    public static UsuarioResponseDto toDTO(Usuario user) {
        if (user == null) {
            return null;
        }

        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setId(user.getId());
        dto.setNome(user.getNome());
        dto.setCpf(user.getCpf());
        dto.setTelefone(user.getTelefone());
        dto.setEmail(user.getEmail());
        dto.setFuncao(user.getFuncao());
        dto.setAfiliados(
                user.getFiliacoes().isEmpty()
                        ? null
                        : user.getFiliacoes()
                        .stream()
                        .map(x -> AlunoMapper.AlunotoDto(x.getAfiliado()))
                        .collect(Collectors.toList())
        );
        dto.setDtNasc(user.getDtNasc());
        dto.setEndereco(EnderecoMapper.toEnderecoDto(user.getEndereco()));

        return dto;
    }

    public static ResponsavelAlunoDto usuarioToResponsavelAlunoDto(Usuario user) {
        if (user == null) {
            return null;
        }

        return ResponsavelAlunoDto.builder()
                .id(user.getId())
                .nome(user.getNome())
                .cpf(user.getCpf())
                .telefone(user.getTelefone())
                .email(user.getEmail())
                .dtNasc(user.getDtNasc())
                .funcao(user.getFuncao())
                .endereco(EnderecoMapper.toEnderecoDto(user.getEndereco()))
                .build();
    }

    public static UsuarioUpdateRequestDto usuarioToUsuarioUpdateRequestDto(Usuario user){
        return UsuarioUpdateRequestDto.builder()
                .dtNasc(user.getDtNasc())
                .endereco(EnderecoMapper.toEnderecoDto(user.getEndereco()))
                .funcao(user.getFuncao())
                .nome(user.getNome())
                .build();
    }

    public static UsuarioTokenDto toTokenDto(Usuario usuario, String token) {
        Integer salaId = null;

        if (usuario.getSala() != null) {
            salaId = usuario.getSala().getId(); // Professor
        } else if (
                usuario.getFiliacoes() != null && !usuario.getFiliacoes().isEmpty() &&
                        usuario.getFiliacoes().get(0).getAfiliado() != null &&
                        usuario.getFiliacoes().get(0).getAfiliado().getSala() != null
        ) {
            salaId = usuario.getFiliacoes().get(0).getAfiliado().getSala().getId(); // Responsável
        } else {
            salaId = null; // Diretor ou sem vínculo
        }

        return UsuarioTokenDto.builder()
                .userId(usuario.getId())
                .email(usuario.getEmail())
                .nome(usuario.getNome())
                .funcao(usuario.getFuncao())
                .telefone(usuario.getTelefone())
                .salaId(salaId)
                .token(token)
                .build();
    }
}
