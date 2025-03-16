package fornari.nucleo.service;

import fornari.nucleo.domain.entity.Aluno;
import fornari.nucleo.domain.entity.AlunoAvaliacao;
import fornari.nucleo.domain.entity.AvaliacaoDimensoes;
import fornari.nucleo.domain.entity.Usuario;
import fornari.nucleo.helper.messages.ConstMessages;
import fornari.nucleo.repository.AlunoAvaliacaoRepository;
import fornari.nucleo.repository.AvaliacaoDimensoesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AvaliacaoDimensoesService {

    private final AvaliacaoDimensoesRepository repository;
    private final AlunoAvaliacaoRepository alunoAvaliacaoRepository;
    private final AlunoService alunoService;
    private final UsuarioService usuarioService;

    public AlunoAvaliacao createAvaliacao(AlunoAvaliacao av, Integer alunoId) {
        Aluno aluno = this.alunoService.findById(alunoId);

        av.setAluno(aluno);
        aluno.addAvaliacao(av);
        av.setDtCriacao(LocalDate.now());

        return this.alunoAvaliacaoRepository.save(av);
    }

    public AlunoAvaliacao findById(Integer id) {
        Optional<AlunoAvaliacao> opt = this.alunoAvaliacaoRepository.findById(id);
        if (opt.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ConstMessages.REGISTER_NOT_FOUND);

        return opt.get();
    }

    public AvaliacaoDimensoes create(AvaliacaoDimensoes av, Integer userId) {
       Usuario user = usuarioService.buscarPorID(userId);

       av.setCriador(user);
       user.addDimensao(av);

       return this.repository.save(av);
    }

    public List<AvaliacaoDimensoes> findByUserAndTipo(Integer userId, String tipo) {
        return this.repository.findAllByCriadorIdAndTipoDimensaoEquals(userId, tipo);
    }

    public void delete(Integer id) {
        this.repository.deleteById(id);
    }

    public void deleteAvaliacao(Integer id) {
        AlunoAvaliacao a = findById(id);

        a.getAluno().removeAvaliacao(a);
        a.setAluno(null);

        this.alunoAvaliacaoRepository.delete(a);
    }
}
