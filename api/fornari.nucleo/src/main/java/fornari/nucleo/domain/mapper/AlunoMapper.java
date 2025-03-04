package fornari.nucleo.domain.mapper;

import fornari.nucleo.domain.dto.FiliacaoAlunoDto;
import fornari.nucleo.domain.dto.aluno.AlunoAndSalaIdDto;
import fornari.nucleo.domain.dto.aluno.AlunoRequestDto;
import fornari.nucleo.domain.dto.aluno.AlunoResponseDto;
import fornari.nucleo.domain.dto.usuario.responsavel.ResponsavelAlunoDto;
import fornari.nucleo.domain.entity.Aluno;
import fornari.nucleo.domain.entity.Usuario;

import java.util.ArrayList;

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
                .laudoNome(aluno.getLaudoNome())
                .observacoes(aluno.getObservacoes())
                .restricoes(RestricaoMapper.multipleRestricaoToRestricaoResponseWithoutAlunosDto(aluno.getRestricoes()))
                .filiacoes(aluno.getFiliacoes().stream().map((x) -> new FiliacaoAlunoDto(
                        UsuarioMapper.usuarioToResponsavelAlunoDto(x.getResponsavel()) , x.getParentesco())).toList())
                .sala(SalaMapper.toSalaResponseDto(aluno.getSala()))
                .recados(aluno.getRecados() == null ? new ArrayList<>() :
                        aluno.getRecados().stream().map(RecadoMapper::recadoToRecadoResponseDto).toList())
                        .build();
    }

    public static AlunoResponseDto AlunoToDtoWithoutSala(Aluno aluno) {
        return AlunoResponseDto.builder()
                .id(aluno.getId())
                .dtNasc(aluno.getDtNasc())
                .laudado(aluno.isLaudado())
                .nome(aluno.getNome())
                .ra(aluno.getRa())
                .laudoNome(aluno.getLaudoNome())
                .observacoes(aluno.getObservacoes())
                .restricoes(RestricaoMapper.multipleRestricaoToRestricaoResponseWithoutAlunosDto(aluno.getRestricoes()))
                .filiacoes(aluno.getFiliacoes().stream().map((x) -> new FiliacaoAlunoDto(
                        UsuarioMapper.usuarioToResponsavelAlunoDto(x.getResponsavel()) , x.getParentesco())).toList())
                .recados(aluno.getRecados() == null ? new ArrayList<>() :
                        aluno.getRecados().stream().map(RecadoMapper::recadoToRecadoResponseDto).toList())
                .build();
    }

    public static Usuario responsavelAlunoDtotoUsuario(ResponsavelAlunoDto dto) {

        if (dto == null) {
            return null;
        }

        return Usuario.builder()
                    .id(dto.getId())
                    .cpf(dto.getCpf())
                    .telefone(dto.getTelefone())
                    .dtNasc(dto.getDtNasc())
                    .email(dto.getEmail())
                    .funcao(dto.getFuncao())
                    .nome(dto.getNome())
                    .endereco(EnderecoMapper.toEndereco(dto.getEndereco()))
                    .build();
    }

    public static AlunoAndSalaIdDto toAlunoAndSalaIdDto(Aluno dto) {
        if (dto == null) return null;

        return AlunoAndSalaIdDto.builder()
                .nome(dto.getNome())
                .idSala(dto.getSala().getId())
                .build();
    }
}
