package fornari.nucleo.service;

import fornari.nucleo.domain.entity.*;
import fornari.nucleo.domain.mapper.UsuarioMapper;
import fornari.nucleo.helper.messages.ConstMessages;
import fornari.nucleo.repository.AlunoRepository;
import fornari.nucleo.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AlunoService {

    private final AlunoRepository repository;

    private final UsuarioService usuarioService;

    private final UsuarioRepository usuarioRepository;

    private final RestricaoService restricaoService;

    public AlunoService(
            AlunoRepository repository,
            UsuarioService usuarioService,
            UsuarioRepository usuarioRepository,
            RestricaoService restricaoService
    ) {
        this.repository = repository;
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.restricaoService = restricaoService;
    }

    @Transactional
    public Aluno create(Aluno aluno, List<Integer> restricoes) {
        if (this.repository.existsByRa(aluno.getRa())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ConstMessages.ALREADY_EXISTS_ALUNO_BY_RA);
        }

        if (aluno.getDtNasc().isBefore(LocalDate.now().minusYears(6))
                || aluno.getDtNasc().isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ConstMessages.INVALID_BIRTHDATE_FOR_ALUNO);
        }

        Optional<Usuario> responsavel = this.usuarioRepository
                .findByCpf(aluno.getFiliacoes().get(0).getResponsavel().getCpf());

        if (responsavel.isPresent()) {
            aluno.getFiliacoes().get(0)
                    .setResponsavel(this.usuarioService.updateUsuario(
                                    aluno.getFiliacoes().get(0).getResponsavel(), responsavel.get().getId()));
        } else {
            aluno.getFiliacoes().get(0)
                    .setResponsavel(this.usuarioService.createUsuario(aluno.getFiliacoes().get(0).getResponsavel()));
        }

        if (aluno.getFiliacoes().stream().filter(x -> Objects.equals(x.getParentesco(), "GENITOR")).toList().size() > 2) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ConstMessages.PARENT_COUNT_EXCEEDED);
        }

        aluno = this.repository.save(aluno);

        this.assignConstraint(aluno, restricoes);

        return this.repository.save(aluno);
    }

    private void assignConstraint(Aluno aluno, List<Integer> restricoes) {
        for (Integer id : restricoes) {
            aluno.addRestricao(this.restricaoService.findById(id));
        }
    }

    public List<Aluno> findAll() {
        return this.repository.findAll();
    }

    public Aluno findById(int id) {
        return this.repository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, ConstMessages.NOT_FOUND_ALUNO_BY_ID.formatted(id)));
    }

    @Transactional
    public Aluno addResponsavel(Filiacao filiacao, Integer alunoId) {
        if (filiacao.getResponsavel().getId() != null) {
            filiacao.setResponsavel(this.usuarioService.updateUsuario(
                    this.usuarioService.buscarPorID(
                            filiacao.getResponsavel().getId()), filiacao.getResponsavel().getId()
            ));
        } else {
            Optional<Usuario> optResponsavel = this.usuarioRepository
                    .findByCpf(filiacao.getResponsavel().getCpf());
            if (optResponsavel.isPresent()) {
                filiacao.setResponsavel(this.usuarioService.updateUsuario(
                        filiacao.getResponsavel(), optResponsavel.get().getId()));
            } else {
                filiacao.setResponsavel(this.usuarioService.createUsuario(filiacao.getResponsavel()));
            }
        }

        Aluno aluno = this.findById(alunoId);
        filiacao.setAfiliado(aluno);

        if (aluno.getFiliacoes().stream()
                .noneMatch(f -> f.getResponsavel().getId().equals(filiacao.getResponsavel().getId()))) {
            aluno.addFiliacao(filiacao);
        }

        if (aluno.getFiliacoes().stream()
                .filter(x -> Objects.equals(x.getParentesco(), "GENITOR")).toList().size() > 2) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ConstMessages.PARENT_COUNT_EXCEEDED);
        }

        return this.repository.save(aluno);
    }

    public Aluno update(Aluno body, List<Integer> restricoes, Integer id) {
        Aluno aluno = this.findById(id);
        if (!body.getRa().equals(aluno.getRa()) && this.repository.existsByRa(body.getRa())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ConstMessages.ALREADY_EXISTS_ALUNO_BY_RA);
        }

        if (body.getDtNasc().isBefore(LocalDate.now().minusYears(6))
                || body.getDtNasc().isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ConstMessages.INVALID_BIRTHDATE_FOR_ALUNO);
        }

        aluno.setNome(body.getNome());
        aluno.setDtNasc(body.getDtNasc());
        aluno.setRa(body.getRa());
        aluno.setObservacoes(body.getObservacoes());
        aluno.setLaudado(body.isLaudado());
        this.assignConstraint(aluno, restricoes);
        return this.repository.save(aluno);
    }

    public void delete(Integer id) {
        this.repository.delete(this.findById(id));
    }

    public Aluno delete(Integer id, Integer userId) {
        Aluno aluno = this.findById(id);
        for (Filiacao filiacao : aluno.getFiliacoes()) {
            if(filiacao.getResponsavel().getId().equals(userId)) {
                aluno.removeFiliacao(filiacao);
                return this.repository.save(aluno);
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, ConstMessages.NOT_REGISTERED_RESPONSIBLE);
    }
}
