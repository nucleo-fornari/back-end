package fornari.nucleo.domain.mapper;

import fornari.nucleo.domain.dto.AlunoAvaliacao.AlunoAvaliacaoDto;
import fornari.nucleo.domain.dto.AlunoAvaliacao.AlunoAvaliacaoRequestDto;
import fornari.nucleo.domain.entity.AlunoAvaliacao;

public class AlunoAvaliacaoMapper {

    public static AlunoAvaliacaoDto toDto(AlunoAvaliacao entity) {
        return AlunoAvaliacaoDto.builder()
                .id(entity.getId())
                .periodo(entity.getPeriodo())
                .textoSocioAfetivaEmocional(entity.getTextoSocioAfetivaEmocional())
                .dtCriacao(entity.getDtCriacao())
                .ano(entity.getAno())
                .textoCognitiva(entity.getTextoCognitiva())
                .textoFisicoMotora(entity.getTextoFisicoMotora())
                .bimestre(entity.getBimestre())
                .build();
    }

    public static AlunoAvaliacao toDomain(AlunoAvaliacaoRequestDto dto) {
        return AlunoAvaliacao.builder()
                .id(null)
                .textoCognitiva(dto.getTextoCognitiva())
                .ano(dto.getAno())
                .bimestre(dto.getBimestre())
                .textoFisicoMotora(dto.getTextoFisicoMotora())
                .textoSocioAfetivaEmocional(dto.getTextoSocioAfetivaEmocional())
                .periodo(dto.getPeriodo())
                .build();
    }
}
