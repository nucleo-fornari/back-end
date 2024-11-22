package fornari.nucleo.domain.mapper;

import fornari.nucleo.domain.dto.recado.RecadoRequestDto;
import fornari.nucleo.domain.dto.recado.RecadoResponseDto;
import fornari.nucleo.domain.entity.Recado;

public class RecadoMapper {

    public static Recado recadoRequestDtoToRecado(RecadoRequestDto recadoRequestDto) {
        if (recadoRequestDto == null) {
            return null;
        }
        Recado recado = new Recado();
        recado.setTitulo(recadoRequestDto.getTitulo());
        recado.setConteudo(recadoRequestDto.getConteudo());
        return recado;
    }

    public static RecadoResponseDto recadoToRecadoResponseDto(Recado recado) {
        RecadoResponseDto recadoResponseDto = new RecadoResponseDto();
        recadoResponseDto.setId(recado.getId());
        recadoResponseDto.setTitulo(recado.getTitulo());
        recadoResponseDto.setConteudo(recado.getConteudo());
        recadoResponseDto.setResponsavel(UsuarioMapper.toDTO(recado.getResponsavel()));
        recadoResponseDto.setDtCriacao(recado.getDtCriacao());
        return recadoResponseDto;
    }
}
