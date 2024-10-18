package fornari.nucleo.controller;

import fornari.nucleo.domain.dto.usuario.UsuarioDefaultDto;
import fornari.nucleo.domain.dto.usuario.UsuarioUpdateRequestDto;
import fornari.nucleo.domain.entity.Usuario;
import fornari.nucleo.domain.mapper.UsuarioMapper;
import fornari.nucleo.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @GetMapping(name = "LIST_USERS")
    public ResponseEntity<List<UsuarioDefaultDto>> getUsers() {
        List<Usuario> users = service.listar();

        if (users.isEmpty()) return ResponseEntity.status(204).build();

        return ResponseEntity.status(200).body(
                users.stream().map(UsuarioMapper::toDTO).toList()
        );
    }

    @PostMapping(value = "/funcionario", name = "CREATE_EMPLOYEE")
    public ResponseEntity<UsuarioDefaultDto> createEmployee(
            @RequestBody UsuarioDefaultDto userDto
    ) {
        Usuario user = service.createUsuario(UsuarioMapper.toUser(userDto));
        return ResponseEntity.status(201).body(UsuarioMapper.toDTO(user));
    }

    @GetMapping(value = "/{id}", name = "GET_USER_BY_ID")
    public ResponseEntity<UsuarioDefaultDto> getById(
            @PathVariable int id
    ) {
        return ResponseEntity.status(200).body(UsuarioMapper.toDTO(service.buscarPorID(id)));
    }

    @DeleteMapping(value = "/{id}", name = "DELETE_USER")
    public ResponseEntity<Void> delete(
            @PathVariable int id
    ) {
        service.delete(service.buscarPorID(id));
        return ResponseEntity.status(200).build();
    }

    @PutMapping(value = "/{id}", name = "UPDATE_USER")
    public ResponseEntity<UsuarioDefaultDto> update(
            @PathVariable int id,
            @RequestBody UsuarioUpdateRequestDto userDTO
    ) {
        Usuario user = service.updateUsuario(userDTO, id);
        return ResponseEntity.status(200).body(UsuarioMapper.toDTO(user));
    }
}
