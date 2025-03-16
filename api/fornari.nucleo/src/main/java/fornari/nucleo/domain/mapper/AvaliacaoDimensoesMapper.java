package fornari.nucleo.domain.mapper;

import fornari.nucleo.domain.dto.AvaliacaoDimensoes.AvaliacaoDimensoesRequestDto;
import fornari.nucleo.domain.dto.AvaliacaoDimensoes.AvaliacaoDimensoesResponseDto;
import fornari.nucleo.domain.entity.AvaliacaoDimensoes;

public class AvaliacaoDimensoesMapper {

    public static AvaliacaoDimensoesResponseDto toDto(AvaliacaoDimensoes e) {
        return AvaliacaoDimensoesResponseDto.builder()
                .id(e.getId())
                .textoDimensao(e.getTextoDimensao())
                .tipoDimensao(e.getTipoDimensao())
                .tituloPreset(e.getTituloPreset())
                .build();
    }

    public static AvaliacaoDimensoes toDomain(AvaliacaoDimensoesRequestDto dto) {
        return AvaliacaoDimensoes.builder()
                .id(dto.getId())
                .textoDimensao(dto.getTextoDimensao())
                .tituloPreset(dto.getTituloPreset())
                .tipoDimensao(dto.getTipoDimensao())
                .build();
    }
}
