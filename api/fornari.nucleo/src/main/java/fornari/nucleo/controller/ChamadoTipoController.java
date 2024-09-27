package fornari.nucleo.controller;

import fornari.nucleo.domain.dto.ChamadoTipoDto;
import fornari.nucleo.domain.mapper.ChamadoTipoMapper;
import fornari.nucleo.service.ChamadoTipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}