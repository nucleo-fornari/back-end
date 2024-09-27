package fornari.nucleo.service;

import fornari.nucleo.domain.dto.UsuarioDto;
import fornari.nucleo.domain.entity.Endereco;
import fornari.nucleo.domain.entity.Usuario;
import fornari.nucleo.domain.mapper.UsuarioMapper;
import fornari.nucleo.helper.Generator;
import fornari.nucleo.helper.messages.ConstMessages;
import fornari.nucleo.helper.validation.GenericValidations;
import fornari.nucleo.repository.UsuarioRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    public Usuario createUsuario(UsuarioDto userDto) {
       return this.repository.save(UsuarioMapper.toUser(userDto));
    }

    public List<Usuario> listar() {
        return repository.findAll();
    }

    public Optional<Usuario> buscarPorID(int id) {
        return repository.findById(id);
    }

    public void deletar(Usuario user) {
        repository.delete(user);
    }

    public Usuario updateUsuario(UsuarioDto userDTO, int id) {
        if (repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Usuario usuario = UsuarioMapper.toUser(userDTO);
        usuario.setId(id);
        return repository.save(usuario);
    }
}
