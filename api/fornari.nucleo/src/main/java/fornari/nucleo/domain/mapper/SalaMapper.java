package fornari.nucleo.domain.mapper;

import fornari.nucleo.domain.dto.sala.SalaRequestDto;
import fornari.nucleo.domain.dto.sala.SalaResponseDto;
import fornari.nucleo.domain.dto.usuario.professor.ProfessorResponseDto;
import fornari.nucleo.domain.entity.Sala;

import java.util.ArrayList;

public class SalaMapper {
    public static SalaResponseDto toSalaResponseDto(Sala sala) {
        if (sala == null) {
            return null;
        }

        if(sala.getAlunos() == null) {
            sala.setAlunos(new ArrayList<>());
        }

        SalaResponseDto dto = SalaResponseDto.builder()
                .id(sala.getId())
                .nome(sala.getNome())
                .localizacao(sala.getLocalizacao())
                .grupo(SalaGrupoMapper.toSalaGrupoResponseDto(sala.getGrupo()))
                .professores(sala.getProfessores().stream().map(ProfessorMapper::UsuarioToProfessorResponseDto).toList())
                .alunos(sala.getAlunos().stream().map(AlunoMapper::AlunoToDtoWithoutSala).toList())
                .build();

        for (ProfessorResponseDto professorResponseDto : dto.getProfessores()) {
            professorResponseDto.setSala(null);
        }

        return dto;
    }

    public static Sala toSala(SalaRequestDto salaRequestDto) {
        if (salaRequestDto == null) {
            return null;
        }
        return Sala.builder()
                .id(null)
                .nome(salaRequestDto.getNome())
                .localizacao(salaRequestDto.getLocalizacao())
                .professores(new ArrayList<>())
                .build();
    }
}
