package fornari.nucleo.service;

import fornari.nucleo.domain.dto.usuario.UsuarioUpdateRequestDto;
import fornari.nucleo.domain.entity.Endereco;
import fornari.nucleo.domain.entity.Usuario;
import fornari.nucleo.domain.mapper.EnderecoMapper;
import fornari.nucleo.helper.Generator;
import fornari.nucleo.helper.messages.ConstMessages;
import fornari.nucleo.helper.validation.GenericValidations;
import fornari.nucleo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;
    private final EnderecoService enderecoService;

    @Transactional
    public Usuario createUsuario(Usuario user) {
        user.setId(null);

        if (!GenericValidations.isValidCpf(user.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ConstMessages.INVALID_CPF);
        }

        user.setEndereco(this.mapEndereco(user.getEndereco()));

        user.setSenha(Generator.generatePassword());
        return this.repository.save(user);
    }

    public Endereco mapEndereco(Endereco endereco) {
        return this.enderecoService.findExistentEndereco(endereco)
                .orElseGet(() -> this.enderecoService.create(endereco));
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

    @Transactional
    public Usuario updateUsuario(Usuario data, int id) {
        if (!GenericValidations.isValidCpf(data.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ConstMessages.INVALID_CPF);
        }

        Usuario user = this.buscarPorID(id);
        user.setEndereco(this.mapEndereco(data.getEndereco()));
        user.setNome(data.getNome());
        user.setCpf(data.getCpf());
        user.setEmail(data.getEmail());
        user.setDtNasc(data.getDtNasc());
        user.setFuncao(data.getFuncao());

        return repository.save(user);
    }
}
