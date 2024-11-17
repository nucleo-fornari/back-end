package fornari.nucleo.domain.mapper;

import fornari.nucleo.domain.dto.EventoCriacaoReqDto;
import fornari.nucleo.domain.dto.EventoRespostaDto;
import fornari.nucleo.domain.entity.Evento;

import java.util.ArrayList;

public class EventoMapper {

    public static Evento toEvento(EventoCriacaoReqDto eventoCriacaoReqDto) {
        if (eventoCriacaoReqDto == null) return null;

        return Evento.builder()
                .titulo(eventoCriacaoReqDto.getTitulo())
                .tipo(eventoCriacaoReqDto.getTipo())
                .data(eventoCriacaoReqDto.getData())
                .descricao(eventoCriacaoReqDto.getDescricao())
                .encerrado(false)
                .build();
    }

    public static EventoRespostaDto toEventoRespostaDto(Evento entidade) {
        if (entidade == null) return null;

        return EventoRespostaDto.builder()
                .id(entidade.getId())
                .titulo(entidade.getTitulo())
                .tipo(entidade.getTipo())
                .descricao(entidade.getDescricao())
                .data(entidade.getData())
                .encerrado(entidade.getEncerrado())
                .salas(entidade.getSalas() == null ? new ArrayList<>() :
                        entidade.getSalas().stream().map(SalaMapper::toSalaResponseDto).toList())
                .build();
    }
}
