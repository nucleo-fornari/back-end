package fornari.nucleo.controller;

import fornari.nucleo.domain.dto.ChamadoTipoDto;
import fornari.nucleo.domain.entity.Chamado;
import fornari.nucleo.domain.entity.ChamadoTipo;
import fornari.nucleo.domain.mapper.ChamadoTipoMapper;
import fornari.nucleo.domain.mapper.UsuarioMapper;
import fornari.nucleo.service.ChamadoTipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos-chamado")
public class ChamadoTipoController {

    @Autowired
    ChamadoTipoService service;

    @PostMapping
    public ResponseEntity<ChamadoTipoDto> create(@RequestBody ChamadoTipoDto chamadoTipo) {
        return ResponseEntity.created(null).body(
                ChamadoTipoMapper.toChamadoTipoDto(
                        service.create(chamadoTipo)
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<ChamadoTipoDto>> list() {
        List<ChamadoTipo> list = this.service.listar();

        if (list.isEmpty()) return ResponseEntity.status(204).build();

        return ResponseEntity.status(200).body(
                list.stream().map(ChamadoTipoMapper::toChamadoTipoDto).toList()
        );
    }
}