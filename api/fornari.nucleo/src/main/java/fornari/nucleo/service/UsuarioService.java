package fornari.nucleo.service;

import fornari.nucleo.domain.dto.usuario.UsuarioEmployeeResponseDto;
import fornari.nucleo.domain.entity.Endereco;
import fornari.nucleo.domain.entity.Usuario;
import fornari.nucleo.domain.mapper.UsuarioMapper;
import fornari.nucleo.helper.Generator;
import fornari.nucleo.helper.messages.ConstMessages;
import fornari.nucleo.helper.validation.GenericValidations;
import fornari.nucleo.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final EnderecoService enderecoService;

    public UsuarioService(
        UsuarioRepository repository,
        EnderecoService enderecoService
    ) {
        this.repository = repository;
        this.enderecoService = enderecoService;
    }

    public Usuario createUsuario(UsuarioEmployeeResponseDto userDto) {
        userDto.setId(null);
        Usuario user = UsuarioMapper.toUser(userDto);

        if (!GenericValidations.isValidCpf(user.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ConstMessages.INVALID_CPF);
        }

        this.mapEndereco(user.getEndereco());

        user.setSenha(Generator.generatePassword());
        return this.repository.save(user);
    }

    public void mapEndereco (Endereco endereco) {

        if (this.enderecoService.alreadyExists(endereco)) {
            endereco = this.enderecoService.findExistentEndereco(endereco).get();
        }
    }

    public List<Usuario> listar() {
        return repository.findAll();
    }

    public Optional<Usuario> buscarPorID(int id) {
        return repository.findById(id);
    }

    public void delete(Usuario user) {
        Endereco endereco = user.getEndereco();
        repository.delete(user);
        if (endereco.getUsuarios().isEmpty()) {
            this.enderecoService.delete(endereco);
        }
    }

    public Usuario updateUsuario(UsuarioEmployeeResponseDto userDTO, int id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Usuario usuario = UsuarioMapper.toUser(userDTO);
        usuario.setId(id);

        this.mapEndereco(usuario.getEndereco());

        return repository.save(usuario);
    }
}
