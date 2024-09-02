package fornari.nucleo.service;

import fornari.nucleo.dto.UsuarioDto;
import fornari.nucleo.entity.Endereco;
import fornari.nucleo.entity.Usuario;
import fornari.nucleo.helper.Generator;
import fornari.nucleo.helper.messages.ConstMessages;
import fornari.nucleo.helper.validation.GenericValidations;
import fornari.nucleo.repository.UsuarioRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    public List<UsuarioDto> mapMultipleUsuarioToUsuarioDto(List<Usuario> users) {
        List<UsuarioDto> dtos = new ArrayList<>();
        for (Usuario user : users) {
            dtos.add(this.mapUsuarioToUsuarioDto(user));
        }

        return dtos;
    }

    public UsuarioDto mapUsuarioToUsuarioDto(Usuario user) {
        UsuarioDto dto = new UsuarioDto();
        dto.setId(user.getId());
        dto.setNome(user.getNome());
        dto.setCpf(user.getCpf());
        dto.setEmail(user.getEmail());
        dto.setEndereco(user.getEndereco());
        dto.setFuncao(user.getFuncao());
        dto.setDtNasc(user.getDtNasc());
        dto.getEndereco().setUsuarios(new ArrayList<>());

        // Descomente a linha de baixo para que a senha seja mostrada no insomnia
        // dto.setSenha(user.getSenha());

        return dto;
    }

    public Usuario createOrUpdateUsuario(UsuarioDto userDto) {
       return this.repository.save(this.mapUsuarioDtoToUsuario(userDto));
    }

    private Usuario mapUsuarioDtoToUsuario(UsuarioDto userDto) {
        Usuario user = userDto.getId() != null ? this.repository.findById(userDto.getId()).get() : new Usuario();

        if (userDto.getNome() != null) {
            user.setNome(userDto.getNome());
        }
        if (userDto.getCpf() != null) {
            if (!GenericValidations.isValidCpf(userDto.getCpf())) {
                throw new ValidationException(ConstMessages.INVALID_CPF);
            }
            user.setCpf(userDto.getCpf());
        }
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getDtNasc() != null) {
            user.setDtNasc(userDto.getDtNasc());
        }
        if (userDto.getFuncao() != null) {
            user.setFuncao(userDto.getFuncao());
        }
        if (user.getSenha() == null) {
            user.setSenha(Generator.generatePassword());
        }

        if (userDto.getEndereco() != null) {
            if (user.getEndereco() == null) {
                user.setEndereco(new Endereco(
                        userDto.getEndereco().getId() != null ? userDto.getEndereco().getId() : null,
                        userDto.getEndereco().getCep(),
                        userDto.getEndereco().getComplemento(),
                        userDto.getEndereco().getBairro(),
                        userDto.getEndereco().getLocalidade(),
                        userDto.getEndereco().getLogradouro(),
                        userDto.getEndereco().getUf(),
                        userDto.getEndereco().getNumero()
                ));
                user.getEndereco().addUser(user);
            } else {
                user.getEndereco().setCep(userDto.getEndereco().getCep());
                user.getEndereco().setComplemento(userDto.getEndereco().getComplemento());
                user.getEndereco().setBairro(userDto.getEndereco().getBairro());
                user.getEndereco().setLocalidade(userDto.getEndereco().getLocalidade());
                user.getEndereco().setUf(userDto.getEndereco().getUf());
                user.getEndereco().setLogradouro(userDto.getEndereco().getLogradouro());
                user.getEndereco().setNumero(userDto.getEndereco().getNumero());
            }
        }

        //Posteriormente mapear filhos de usuários do tipo "RESPONSAVEL" aqui

        return user;
    }
}
