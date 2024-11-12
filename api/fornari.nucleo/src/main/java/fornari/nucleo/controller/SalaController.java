package fornari.nucleo.controller;

import fornari.nucleo.domain.dto.sala.SalaRequestDto;
import fornari.nucleo.domain.dto.sala.SalaResponseDto;
import fornari.nucleo.domain.entity.Sala;
import fornari.nucleo.domain.mapper.SalaMapper;
import fornari.nucleo.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/salas")
public class SalaController {
    @Autowired
    private SalaService salaService;

    @PostMapping
    public ResponseEntity<SalaResponseDto> create(SalaRequestDto data) {
        return ResponseEntity.status(201).body(SalaMapper.toSalaResponseDto(salaService.create(SalaMapper.toSala(data))));
    }

    @GetMapping
    public ResponseEntity<List<SalaResponseDto>> list() {
        List<Sala> list = salaService.list();

        if (list.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(list.stream().map(SalaMapper::toSalaResponseDto).toList());
    }
}
