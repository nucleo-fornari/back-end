package fornari.nucleo.domain.mapper;

import fornari.nucleo.domain.dto.sala.SalaResponseDto;
import fornari.nucleo.domain.dto.usuario.professor.ProfessorResponseDto;
import fornari.nucleo.domain.entity.Usuario;

public class ProfessorMapper {
    public static ProfessorResponseDto UsuarioToProfessorResponseDto(Usuario usuario) {
        if (usuario == null) return null;

        ProfessorResponseDto.SalaProfessorDto sala = usuario.getSala() == null ? null :
                ProfessorResponseDto.SalaProfessorDto.builder()
                .id(usuario.getSala().getId())
                .localizacao(usuario.getSala().getLocalizacao())
                .nome(usuario.getSala().getNome())
                .build();

        return ProfessorResponseDto.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .cpf(usuario.getCpf())
                .telefone(usuario.getTelefone())
                .email(usuario.getEmail())
                .dtNasc(usuario.getDtNasc())
                .funcao(usuario.getFuncao())
                .endereco(EnderecoMapper.toEnderecoDto(usuario.getEndereco()))
                .sala(sala)
                .build();
    }
}
