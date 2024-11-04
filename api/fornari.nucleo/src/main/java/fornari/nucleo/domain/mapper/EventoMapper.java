package fornari.nucleo.domain.mapper;

import fornari.nucleo.domain.dto.EventoCriacaoReqDto;
import fornari.nucleo.domain.dto.EventoRespostaDto;
import fornari.nucleo.domain.entity.Evento;

public class EventoMapper {

    public static Evento toEvento(EventoCriacaoReqDto eventoCriacaoReqDto) {
        if (eventoCriacaoReqDto == null) return null;

        return Evento.builder()
                .titulo(eventoCriacaoReqDto.getTitulo())
                .tipo(eventoCriacaoReqDto.getTitulo())
                .local(eventoCriacaoReqDto.getTitulo())
                .data(eventoCriacaoReqDto.getData())
                .descricao(eventoCriacaoReqDto.getDescricao())
                .encerrado(eventoCriacaoReqDto.getEncerrado())
                .build();
    }

    public static EventoRespostaDto toEventoRespostaDto(Evento entidade) {
        if (entidade == null) return null;

        return EventoRespostaDto.builder()
                .titulo(entidade.getTitulo())
                .tipo(entidade.getTipo())
                .local(entidade.getLocal())
                .descricao(entidade.getDescricao())
                .data(entidade.getData())
                .encerrado(entidade.getEncerrado())
                .build();
    }
}
