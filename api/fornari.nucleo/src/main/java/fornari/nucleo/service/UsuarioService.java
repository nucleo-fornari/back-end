package fornari.nucleo.service;

import fornari.nucleo.domain.dto.usuario.UsuarioEmployeeResponseDto;
import fornari.nucleo.domain.entity.Endereco;
import fornari.nucleo.domain.entity.Usuario;
import fornari.nucleo.domain.mapper.UsuarioMapper;
import fornari.nucleo.helper.Generator;
import fornari.nucleo.helper.messages.ConstMessages;
import fornari.nucleo.helper.validation.GenericValidations;
import fornari.nucleo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;
    private final EnderecoService enderecoService;

    public Usuario createUsuario(Usuario user) {
        user.setId(null);

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

    public Usuario buscarPorID(int id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void delete(Usuario user) {
        Endereco endereco = user.getEndereco();
        repository.delete(user);
        if (endereco.getUsuarios().isEmpty()) {
            this.enderecoService.delete(endereco);
        }
    }

    public Usuario updateUsuario(Usuario usuario, int id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (!GenericValidations.isValidCpf(usuario.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ConstMessages.INVALID_CPF);
        }

        this.mapEndereco(usuario.getEndereco());

        return repository.save(usuario);
    }
}
