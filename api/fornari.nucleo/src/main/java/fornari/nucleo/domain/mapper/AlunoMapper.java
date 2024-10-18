package fornari.nucleo.domain.mapper;

import fornari.nucleo.domain.dto.FiliacaoAlunoDto;
import fornari.nucleo.domain.dto.aluno.AlunoCreationRequestDto;
import fornari.nucleo.domain.dto.aluno.AlunoResponseDto;
import fornari.nucleo.domain.entity.Aluno;
import fornari.nucleo.domain.entity.Filiacao;
import fornari.nucleo.domain.entity.Usuario;

import java.util.ArrayList;
import java.util.List;

public class AlunoMapper {
    public static Aluno alunoCreationRequestDtotoAluno(AlunoCreationRequestDto dto) {

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
                .observacoes(dto.getObservacoes())
                .build();

        Usuario responsavel = Usuario.builder()
                .id(dto.getFiliacao().getResponsavel().getId())
                .cpf(dto.getFiliacao().getResponsavel().getCpf())
                .dtNasc(dto.getFiliacao().getResponsavel().getDtNasc())
                .email(dto.getFiliacao().getResponsavel().getEmail())
                .funcao(dto.getFiliacao().getResponsavel().getFuncao())
                .nome(dto.getFiliacao().getResponsavel().getNome())
                .endereco(EnderecoMapper.toEndereco(dto.getFiliacao().getResponsavel().getEndereco()))
                .build();

        List<Filiacao> filiacoes = new ArrayList<>(List.of(new Filiacao(aluno, responsavel, dto.getFiliacao().getParentesco())));

        aluno.setFiliacoes(filiacoes);
        responsavel.setFiliacoes(filiacoes);

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
                        .build();
    }
}
