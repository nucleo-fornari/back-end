package fornari.nucleo.service;

import fornari.nucleo.dto.ChamadoTipoDto;
import fornari.nucleo.entity.ChamadoTipo;
import fornari.nucleo.repository.ChamadoTipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ChamadoTipoService {
    
    @Autowired 
    private ChamadoTipoRepository repository;

    @Autowired
    private ChamadoService chamadoService;
    public ChamadoTipoDto mapChamadoTipoToChamadoTipoDto(ChamadoTipo chamadoTipo) {
        ChamadoTipoDto dto = new ChamadoTipoDto();

        dto.setId(chamadoTipo.getId());
        dto.setTipo(chamadoTipo.getTipo());
        dto.setPrioridade(chamadoTipo.getPrioridade());

        return dto;
    }

    public ChamadoTipoDto createOrUpdateChamadoTipo(ChamadoTipoDto dto) {
        return this.mapChamadoTipoToChamadoTipoDto(
                this.repository.save(
                        this.mapChamadoTipoDtoToChamadoTipo(dto)));
    }

    private ChamadoTipo mapChamadoTipoDtoToChamadoTipo(ChamadoTipoDto dto) {
        ChamadoTipo chamadoTipo = dto.getId() != null ? this.repository.findById(dto.getId()).get() : new ChamadoTipo();

        chamadoTipo.setId(dto.getId());
        chamadoTipo.setTipo(dto.getTipo());
        chamadoTipo.setPrioridade(dto.getPrioridade());
        return chamadoTipo;
    }

    public ChamadoTipoDto create(ChamadoTipoDto dto) {
        dto.setId(null);
        return this.createOrUpdateChamadoTipo(dto);
    }
}
