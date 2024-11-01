package fornari.nucleo.domain.mapper;

import fornari.nucleo.domain.dto.FiliacaoAlunoDto;
import fornari.nucleo.domain.entity.Filiacao;
import fornari.nucleo.domain.entity.Usuario;

public class FiliacaoMapper {

    public static Filiacao filiacaoAlunoDtoToFiliacao(FiliacaoAlunoDto dto) {
        return dto == null ? null : Filiacao.builder()
                .parentesco(dto.getParentesco())
                .responsavel(Usuario.builder()
                        .id(dto.getResponsavel().getId())
                        .cpf(dto.getResponsavel().getCpf())
                        .dtNasc(dto.getResponsavel().getDtNasc())
                        .email(dto.getResponsavel().getEmail())
                        .funcao(dto.getResponsavel().getFuncao())
                        .nome(dto.getResponsavel().getNome())
                        .endereco(EnderecoMapper.toEndereco(dto.getResponsavel().getEndereco()))
                        .build())
                .build();

    }
}
