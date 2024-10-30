package fornari.nucleo.domain.mapper;

import fornari.nucleo.domain.dto.ChamadoDto;
import fornari.nucleo.domain.dto.ChamadoTipoDto;
import fornari.nucleo.domain.entity.Chamado;

import java.time.LocalDateTime;

public class ChamadoMapper {

    public static ChamadoDto ToChamadoDto(Chamado chamado) {
        if (chamado == null) return null;

        ChamadoDto dto = new ChamadoDto();

        dto.setFinalizado(chamado.isFinalizado());
        dto.setDescricao(chamado.getDescricao());
        dto.setId(chamado.getId());
        dto.setDtAbertura(chamado.getDtAbertura());
        dto.setDtFechamento(chamado.getDtFechamento());
        dto.setCriancaAtipica(chamado.isCriancaAtipica());
        dto.setTipo(ChamadoTipoMapper.toChamadoTipoDto(chamado.getTipo()));
        dto.setResponsavel(UsuarioMapper.toDTO(chamado.getResponsavel()));

        return dto;
    }


    public static Chamado toChamado(ChamadoDto dto) {
        if (dto == null) return null;

        Chamado chamado = new Chamado();

        chamado.setId(dto.getId());
        chamado.setDescricao(dto.getDescricao());
        chamado.setFinalizado(dto.isFinalizado());
        chamado.setDtAbertura(dto.getDtAbertura());
        chamado.setDtFechamento(dto.getDtFechamento());
        chamado.setCriancaAtipica(dto.isCriancaAtipica());
        chamado.setTipo(ChamadoTipoMapper.toChamadoTipo(dto.getTipo()));

        return chamado;
    }
}
