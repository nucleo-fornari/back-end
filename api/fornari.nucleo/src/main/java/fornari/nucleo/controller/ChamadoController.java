package fornari.nucleo.controller;

import fornari.nucleo.domain.dto.ChamadoDto;
import fornari.nucleo.domain.mapper.ChamadoMapper;
import fornari.nucleo.service.ChamadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {

    @Autowired
    private ChamadoService service;

    @GetMapping("/abertos")
    public ResponseEntity<List<ChamadoDto>> list() {
        return ResponseEntity.ok().body(
                service.findByFinalizadoEqualsFalse().stream().map(ChamadoMapper::ToChamadoDto).toList()
        );
    }

    @PostMapping
    public ResponseEntity<ChamadoDto> create(@RequestBody ChamadoDto dto, @RequestParam Integer idUsuario) {
        return ResponseEntity.status(201).body(
                ChamadoMapper.ToChamadoDto(service.create(dto, idUsuario))
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> finishChamado(@PathVariable Integer id) {
        this.service.finish(id);
        return ResponseEntity.noContent().build();
    }
}
