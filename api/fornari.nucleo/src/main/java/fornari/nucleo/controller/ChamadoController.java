package fornari.nucleo.controller;

import fornari.nucleo.dto.ChamadoDto;
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
        return ResponseEntity.ok().body(this.service.findByFinalizadoEqualsFalse());
    }

    @PostMapping
    public ResponseEntity<ChamadoDto> create(@RequestBody ChamadoDto dto) {
        return ResponseEntity.ok().body(this.service.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> finishChamado(@PathVariable Integer id) {
        this.service.finish(id);
        return ResponseEntity.noContent().build();
    }
}
