package fornari.nucleo.domain.mapper;

import fornari.nucleo.domain.dto.FiliacaoAlunoDto;
import fornari.nucleo.domain.dto.aluno.AlunoRequestDto;
import fornari.nucleo.domain.dto.aluno.AlunoResponseDto;
import fornari.nucleo.domain.dto.usuario.responsavel.ResponsavelAlunoDto;
import fornari.nucleo.domain.entity.Aluno;
import fornari.nucleo.domain.entity.Usuario;

import java.util.ArrayList;
import java.util.List;

public class AlunoMapper {
    public static Aluno alunoCreationRequestDtotoAluno(AlunoRequestDto dto) {

        if (dto == null) {
            return null;
        }

        Aluno aluno = Aluno.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .laudado(dto.isLaudado())
                .ra(dto.getRa())
                .dtNasc(dto.getDtNasc())
                .restricoes(new ArrayList<>())
                .filiacoes(new ArrayList<>())
                .observacoes(dto.getObservacoes())
                .build();

        return aluno;
    }

    public static AlunoResponseDto AlunotoDto(Aluno aluno) {

        return AlunoResponseDto.builder()
                .id(aluno.getId())
                .dtNasc(aluno.getDtNasc())
                .laudado(aluno.isLaudado())
                .nome(aluno.getNome())
                .ra(aluno.getRa())
                .observacoes(aluno.getObservacoes())
                .restricoes(RestricaoMapper.multipleRestricaoToRestricaoResponseWithoutAlunosDto(aluno.getRestricoes()))
                .filiacoes(aluno.getFiliacoes().stream().map((x) -> new FiliacaoAlunoDto(
                        UsuarioMapper.usuarioToResponsavelAlunoDto(x.getResponsavel()) , x.getParentesco())).toList())
                .sala(SalaMapper.toSalaResponseDto(aluno.getSala()))
                        .build();
    }

    public static Usuario responsavelAlunoDtotoUsuario(ResponsavelAlunoDto dto) {

        if (dto == null) {
            return null;
        }

        return Usuario.builder()
                    .id(dto.getId())
                    .cpf(dto.getCpf())
                    .dtNasc(dto.getDtNasc())
                    .email(dto.getEmail())
                    .funcao(dto.getFuncao())
                    .nome(dto.getNome())
                    .endereco(EnderecoMapper.toEndereco(dto.getEndereco()))
                    .build();
    }
}
