package fornari.nucleo.controller;

import fornari.nucleo.domain.dto.usuario.*;
import fornari.nucleo.domain.entity.Usuario;
import fornari.nucleo.domain.mapper.UsuarioMapper;
import fornari.nucleo.service.UsuarioService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<UsuarioResponseDto>> getUsers() {
        List<Usuario> list = service.listar();

        if (list.isEmpty()) return ResponseEntity.status(204).build();

        return ResponseEntity.status(200).body(
                list.stream().map(UsuarioMapper::toDTO).toList()
        );
    }

    @PostMapping(value = "/funcionario", name = "CREATE_EMPLOYEE")
    public ResponseEntity<UsuarioResponseDto> createEmployee(
            @RequestBody @Valid UsuarioCreateDto userDto
    ) {
        Usuario user = service.createUsuario(UsuarioMapper.toUser(userDto));
        return ResponseEntity.status(201).body(UsuarioMapper.toDTO(user));
    }

    @GetMapping(value = "/{id}", name = "GET_USER_BY_ID")
    public ResponseEntity<UsuarioResponseDto> getById(
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
    public ResponseEntity<UsuarioResponseDto> update(
            @PathVariable int id,
            @RequestBody @Valid UsuarioUpdateRequestDto userDTO
    ) {
        Usuario user = service.updateUsuario(UsuarioMapper.toUser(userDTO), id);
        return ResponseEntity.status(200).body(UsuarioMapper.toDTO(user));
    }

    @GetMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody @Valid UsuarioLoginDto usuarioLoginDto) {
        return ResponseEntity.ok(
                service.autenticar(usuarioLoginDto.getEmail(), usuarioLoginDto.getSenha())
        );
    }
}
