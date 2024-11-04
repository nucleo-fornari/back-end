package fornari.nucleo.domain.mapper;

import fornari.nucleo.domain.dto.ChamadoDto;
import fornari.nucleo.domain.dto.ChamadoTipoDto;
import fornari.nucleo.domain.entity.ChamadoTipo;

public class ChamadoTipoMapper {

    public static ChamadoTipo toChamadoTipo(ChamadoTipoDto dto) {
        if (dto == null) return null;

        ChamadoTipo chamadoTipo = new ChamadoTipo();
        chamadoTipo.setId(dto.getId());
        chamadoTipo.setTipo(dto.getTipo());
        chamadoTipo.setPrioridade(dto.getPrioridade());

        return chamadoTipo;
    }

    public static ChamadoTipoDto toChamadoTipoDto(ChamadoTipo chamadoTipo) {
        ChamadoTipoDto dto = new ChamadoTipoDto();
        dto.setId(chamadoTipo.getId());
        dto.setTipo(chamadoTipo.getTipo());
        dto.setPrioridade(chamadoTipo.getPrioridade());

        return dto;
    }
}
