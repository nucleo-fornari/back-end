package fornari.nucleo.domain.mapper;

import fornari.nucleo.domain.dto.sala.SalaRequestDto;
import fornari.nucleo.domain.dto.sala.grupo.SalaGrupoRequestDto;
import fornari.nucleo.domain.dto.sala.grupo.SalaGrupoResponseDto;
import fornari.nucleo.domain.entity.SalaGrupo;

public class SalaGrupoMapper {

    public static SalaGrupoResponseDto toSalaGrupoResponseDto(SalaGrupo salaGrupo) {
        return SalaGrupoResponseDto
                .builder()
                .id(salaGrupo.getId())
                .nome(salaGrupo.getNome())
                .build();
    }

    public static SalaGrupo toSalaGrupo(SalaGrupoRequestDto salaGrupoRequestDto) {
        return salaGrupoRequestDto == null ? null : SalaGrupo
                .builder()
                .id(null)
                .nome(salaGrupoRequestDto.getNome())
                .build();
    }
}
