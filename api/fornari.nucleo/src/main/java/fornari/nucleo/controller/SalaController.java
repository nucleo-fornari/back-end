package fornari.nucleo.controller;

import fornari.nucleo.domain.dto.sala.SalaRequestDto;
import fornari.nucleo.domain.dto.sala.SalaResponseDto;
import fornari.nucleo.domain.dto.sala.grupo.SalaGrupoRequestDto;
import fornari.nucleo.domain.dto.sala.grupo.SalaGrupoResponseDto;
import fornari.nucleo.domain.entity.Sala;
import fornari.nucleo.domain.entity.SalaGrupo;
import fornari.nucleo.domain.mapper.SalaGrupoMapper;
import fornari.nucleo.domain.mapper.SalaMapper;
import fornari.nucleo.domain.mapper.UsuarioMapper;
import fornari.nucleo.service.SalaGrupoService;
import fornari.nucleo.service.SalaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salas")
@RequiredArgsConstructor
public class SalaController {

    private final SalaService salaService;
    private final SalaGrupoService salaGrupoService;

    @PostMapping
    public ResponseEntity<SalaResponseDto> create(@RequestBody @Valid SalaRequestDto data) {
        return ResponseEntity.status(201).body(SalaMapper.toSalaResponseDto(salaService.create(SalaMapper.toSala(data), data.getGrupoId())));
    }

    @GetMapping
    public ResponseEntity<List<SalaResponseDto>> list() {
        List<Sala> list = salaService.list();

        if (list.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(list.stream().map(SalaMapper::toSalaResponseDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaResponseDto> getById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(SalaMapper.toSalaResponseDto(salaService.findById(id)));
    }

    @PostMapping("/grupos/{nome}")
    public ResponseEntity<SalaGrupoResponseDto> createSalaGrupo(@PathVariable String nome) {
        return ResponseEntity.status(201).body(SalaGrupoMapper.toSalaGrupoResponseDto(
                salaGrupoService.create(nome)));
    }

    @GetMapping("/grupos")
    public ResponseEntity<List<SalaGrupoResponseDto>> listSalaGrupos() {
        List<SalaGrupo> list = salaGrupoService.list();

        if (list.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(list.stream().map(SalaGrupoMapper::toSalaGrupoResponseDto).toList());
    }
}