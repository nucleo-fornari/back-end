package fornari.nucleo.controller;

import fornari.nucleo.domain.dto.UsuarioDto;
import fornari.nucleo.domain.entity.Usuario;
import fornari.nucleo.domain.mapper.UsuarioMapper;
import fornari.nucleo.repository.UsuarioRepository;
import fornari.nucleo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
//@Tag(name = "Usuário")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @GetMapping(name = "LIST_USERS")
    public ResponseEntity<List<UsuarioDto>> getUsers() {
        List<Usuario> users = service.listar();

        if (users.isEmpty()) return ResponseEntity.status(204).build();

        return ResponseEntity.status(200).body(
                users.stream().map(UsuarioMapper::toDTO).toList()
        );
    }

    @PostMapping(value = "/funcionario", name = "CREATE_EMPLOYEE")
    public ResponseEntity<UsuarioDto> createEmployee(
            @RequestBody UsuarioDto userDto
    ) {
        userDto.setId(null);
        Usuario user = service.createUsuario(userDto);

        return ResponseEntity.status(201).body(UsuarioMapper.toDTO(user));
    }

    @GetMapping(value = "/{id}", name = "GET_USER_BY_ID")
    public ResponseEntity<UsuarioDto> getById(
            @PathVariable int id
    ) {
        Optional<Usuario> user = service.buscarPorID(id);

        return user.isPresent() ?
                ResponseEntity.status(200).body(UsuarioMapper.toDTO(user.get())) :
                ResponseEntity.status(204).build();
    }

    @DeleteMapping(value = "/{id}", name = "DELETE_USER")
    public ResponseEntity<Void> delete(
            @PathVariable int id
    ) {
        Optional<Usuario> user = service.buscarPorID(id);

        if (user.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        service.deletar(user.get());
        return ResponseEntity.status(200).build();
    }

    @PutMapping(value = "/{id}", name = "UPDATE_USER")
    public ResponseEntity<UsuarioDto> update(
            @PathVariable int id,
            @RequestBody UsuarioDto userDTO
    ) {
        Usuario user = service.updateUsuario(userDTO, id);
        return ResponseEntity.status(200).body(UsuarioMapper.toDTO(user));
    }
}
