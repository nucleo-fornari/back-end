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
                    .setResponsavel(this.usuarioService.updateUsuario(UsuarioMapper
                            .usuarioToUsuarioUpdateRequestDto(
                                    aluno.getFiliacoes().get(0).getResponsavel()), responsavel.get().getId()));
        } else {
            aluno.getFiliacoes().get(0)
                    .setResponsavel(this.usuarioService.createUsuario(aluno.getFiliacoes().get(0).getResponsavel()));
        }

        if (aluno.getFiliacoes().stream().filter(x -> Objects.equals(x.getParentesco(), "GENITOR")).toList().size() > 2) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ConstMessages.PARENT_COUNT_EXCEEDED);
        }

        aluno = this.repository.save(aluno);

        for (Integer id : restricoes) {
            aluno.addRestricao(this.restricaoService.findById(id));
        }

        return this.repository.save(aluno);
    }

    public List<Aluno> findAll() {
        return this.repository.findAll();
    }

    public Aluno findById(int id) {
        return this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ConstMessages.NOT_FOUND_ALUNO_BY_ID.formatted(id)));
    }

    @Transactional
    public Aluno addResponsavel(Filiacao filiacao, Integer alunoId) {
        Optional<Usuario> optResponsavel = this.usuarioRepository
                .findByCpf(filiacao.getResponsavel().getCpf());
        if (optResponsavel.isPresent()) {
            filiacao.setResponsavel(this.usuarioService.updateUsuario(UsuarioMapper
                    .usuarioToUsuarioUpdateRequestDto(filiacao.getResponsavel()), optResponsavel.get().getId()));
        } else {
            filiacao.setResponsavel(this.usuarioService.createUsuario(filiacao.getResponsavel()));
        }

        Aluno aluno = this.findById(alunoId);
        aluno.addFiliacao(filiacao);

        if (aluno.getFiliacoes().stream().filter(x -> x.getResponsavel().getId().equals(filiacao.getResponsavel().getId())).toList().size() > 1) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ConstMessages.ALREADY_RESGISTERED_RESPONSIBLE_FOR_ALUNO);
        }

        return this.repository.save(aluno);
    }
}
