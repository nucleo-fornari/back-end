package fornari.nucleo.controller;

import fornari.nucleo.dto.UsuarioDto;
import fornari.nucleo.entity.Usuario;
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
    private UsuarioRepository repository;

    @Autowired
    private UsuarioService service;

    @GetMapping(name = "LIST_USERS")
    public ResponseEntity<List<UsuarioDto>> getUsers() {
        List<Usuario> users = this.repository.findAll();

        if (!users.isEmpty()) {
            return ResponseEntity.status(200).body(this.service.mapMultipleUsuarioToUsuarioDto(users));
        }

        return ResponseEntity.status(204).build();
    }

    @PostMapping(value = "/funcionario", name = "CREATE_EMPLOYEE")
    public ResponseEntity<UsuarioDto> createEmployee(
            @RequestBody UsuarioDto user
    ) {
        user.setId(null);
        Usuario savedUser = this.service.createOrUpdateUsuario(user);

        if (savedUser.getId() != null) {
            return ResponseEntity.status(201).body(this.service.mapUsuarioToUsuarioDto(savedUser));
        }
        return ResponseEntity.status(400).build();
    }

    @GetMapping(value = "/{id}", name = "GET_USER_BY_ID")
    public ResponseEntity<UsuarioDto> getById(
            @PathVariable int id
    ) {
        Optional<Usuario> user = this.repository.findById(id);

        return user.map(usuario -> ResponseEntity.status(200).body(this.service.mapUsuarioToUsuarioDto(usuario)))
                .orElseGet(() -> ResponseEntity.status(204).build());

    }

    @DeleteMapping(value = "/{id}", name = "DELETE_USER")
    public ResponseEntity<Void> delete(
            @PathVariable int id
    ) {
        Optional<Usuario> user = this.repository.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        this.repository.delete(user.get());
        return ResponseEntity.status(200).build();
    }

    @PutMapping(value = "/{id}", name = "UPDATE_USER")
    public ResponseEntity<UsuarioDto> update(
            @PathVariable int id,
            @RequestBody UsuarioDto user
    ) {
        user.setId(id);
        Usuario savedUser = this.service.createOrUpdateUsuario(user);

        if (savedUser.getId() != null) {
            return ResponseEntity.status(200).body(this.service.mapUsuarioToUsuarioDto(savedUser));
        }
        return ResponseEntity.status(400).build();
    }
}
