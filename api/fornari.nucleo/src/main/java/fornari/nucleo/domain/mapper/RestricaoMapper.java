package fornari.nucleo.domain.mapper;

import fornari.nucleo.domain.dto.restricao.RestricaoCreationRequestDto;
import fornari.nucleo.domain.dto.restricao.RestricaoResponseWithoutAlunosDto;
import fornari.nucleo.domain.entity.Restricao;

import java.util.ArrayList;
import java.util.List;

public class RestricaoMapper {
    public static Restricao restricaoCreationRequestDtoToRestricao(RestricaoCreationRequestDto dto) {
       return  dto == null ? null : Restricao.builder()
                .tipo(dto.getTipo())
                .descricao(dto.getDescricao())
                .build();
    }

    public static RestricaoResponseWithoutAlunosDto restricaoToRestricaoResponseWithoutAlunosDto(Restricao restricao) {
        return  restricao == null ? null : RestricaoResponseWithoutAlunosDto.builder()
                .tipo(restricao.getTipo())
                .id(restricao.getId())
                .descricao(restricao.getDescricao())
                .build();
    }

    public static List<RestricaoResponseWithoutAlunosDto> multipleRestricaoToRestricaoResponseWithoutAlunosDto(List<Restricao> restricoes) {
        return restricoes.stream().map(RestricaoMapper::restricaoToRestricaoResponseWithoutAlunosDto).toList();
    }
}
