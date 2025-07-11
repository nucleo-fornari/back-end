package fornari.nucleo.service;

import fornari.nucleo.domain.dto.aluno.AlunoPutDto;
import fornari.nucleo.domain.entity.*;
import fornari.nucleo.domain.mapper.UsuarioMapper;
import fornari.nucleo.helper.messages.ConstMessages;
import fornari.nucleo.repository.AlunoRepository;
import fornari.nucleo.repository.FiliacaoRepository;
import fornari.nucleo.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.*;

@Service
public class AlunoService {

    private final AlunoRepository repository;

    private final FiliacaoRepository filiacaoRepository;

    private final UsuarioService usuarioService;

    private final UsuarioRepository usuarioRepository;

    private final RestricaoService restricaoService;

    private final SalaService salaService;

    private final FileStorageService fileStorageService;

    public AlunoService(
            AlunoRepository repository,
            UsuarioService usuarioService,
            UsuarioRepository usuarioRepository,
            RestricaoService restricaoService,
            FiliacaoRepository filiacaoRepository,
            SalaService salaService,
            FileStorageService fileStorageService
    ) {
        this.repository = repository;
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.restricaoService = restricaoService;
        this.filiacaoRepository = filiacaoRepository;
        this.salaService = salaService;
        this.fileStorageService = fileStorageService;
    }

    @Transactional
    public Aluno create(Aluno aluno, Usuario responsavel, String parentesco, List<Integer> restricoes, MultipartFile file) {

        if (this.repository.existsByRa(aluno.getRa())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ConstMessages.ALREADY_EXISTS_ALUNO_BY_RA);
        }

        if (aluno.getDtNasc().isBefore(LocalDate.now().minusYears(6))
                || aluno.getDtNasc().isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ConstMessages.INVALID_BIRTHDATE_FOR_ALUNO);
        }

        Optional<Usuario> optResponsavel = this.usuarioRepository
                .findByCpf(responsavel.getCpf());

        if (optResponsavel.isPresent()) {
            responsavel = (this.usuarioService.updateUsuario(
                                    responsavel, optResponsavel.get().getId()));
        } else {
            responsavel = (this.usuarioService.createUsuario(responsavel));
        }

        new Filiacao(null, aluno, responsavel, parentesco);

        this.updateConstraint(aluno, restricoes);

        if (aluno.isLaudado()){
            String fileName = this.fileStorageService.storeFile(file);
            aluno.setLaudoNome(fileName);
        }

        return this.repository.save(aluno);
    }

    private void updateConstraint(Aluno aluno, List<Integer> restricoes) {
        this.repository.save(aluno);

        for (Restricao restricao : aluno.getRestricoes()) {
            restricao.removeAluno(aluno);
        }

        aluno.getRestricoes().clear();

        this.assignConstraint(aluno, restricoes);
    }

    // IMPLEMENTAÇÃO RECURSÃO
    private void assignConstraint(Aluno aluno, List<Integer> restricoes) {
        if (restricoes == null || restricoes.isEmpty()) {
            return;  // Caso base: lista vazia ou nula, termina a recursão
        }

        // Pega o primeiro elemento da lista e associa a restrição ao aluno
        Integer id = restricoes.get(0);
        aluno.addRestricao(this.restricaoService.findById(id));

        // Chama o mét0do recursivamente com o restante da lista
        assignConstraint(aluno, restricoes.subList(1, restricoes.size()));
    }

    public List<Aluno> findAll() {
        return this.repository.findAll();
    }

    public Aluno findById(Integer id) {
        return this.repository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, ConstMessages.NOT_FOUND_ALUNO_BY_ID.formatted(id)));
    }

    @Transactional
    public Aluno addResponsavel(Usuario responsavel, Integer alunoId, String parentesco) {

        Aluno aluno = this.findById(alunoId);

        if (responsavel.getId() != null) {
             responsavel = (this.usuarioService.updateUsuario(
                    this.usuarioService.buscarPorID(
                            responsavel.getId()), responsavel.getId()
            ));
        } else {
            Optional<Usuario> optResponsavel = this.usuarioRepository.findByCpf(responsavel.getCpf());
            if (optResponsavel.isPresent()) {
                responsavel = (this.usuarioService.updateUsuario(responsavel, optResponsavel.get().getId()));
            } else {
                responsavel = (this.usuarioService.createUsuario(responsavel));
                responsavel.setFiliacoes(new ArrayList<>());
            }
        }

        Usuario finalResponsavel = responsavel;

        if (aluno.getFiliacoes().stream()
                .noneMatch(f -> f.getResponsavel().getId().equals(finalResponsavel.getId()))) {
            Filiacao filiacao = filiacaoRepository.save(new Filiacao(null, aluno, responsavel, parentesco));
            aluno.addFiliacao(filiacao);
            responsavel.addFiliacao(filiacao);
        }

        if (aluno.getFiliacoes().stream()
                .filter(x -> Objects.equals(x.getParentesco(), "GENITOR")).toList().size() > 2) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ConstMessages.PARENT_COUNT_EXCEEDED);
        }

        return this.repository.save(aluno);
    }

    @Transactional
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
        this.updateConstraint(aluno, restricoes);
        return this.repository.save(aluno);
    }

    public void delete(Integer id) {
        this.repository.delete(this.findById(id));
    }

    public void delete(Integer id, Integer userId) {
        Aluno aluno = this.findById(id);

        if (aluno.getFiliacoes().size() < 2) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    ConstMessages.ALUNO_NEEDS_AT_LEAST_ONE_RESPONSIBLE);
        }

        Iterator<Filiacao> iterator = aluno.getFiliacoes().iterator();
        boolean removed = false;

        while (iterator.hasNext()) {
            Filiacao filiacao = iterator.next();

            if (filiacao.getResponsavel().getId().equals(userId)) {
                iterator.remove();
                this.repository.save(aluno);
                usuarioService.delete(usuarioRepository.getById(userId));
                removed = true;
                break;
            }
        }

        if (!removed) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ConstMessages.NOT_REGISTERED_RESPONSIBLE);
        }
    }

    public Aluno enrollStudentWithClassroom(Integer id, Integer classroomId) {
        Sala sala = salaService.findById(classroomId);
        Aluno aluno = this.findById(id);
        sala.addAluno(aluno);
        return this.repository.save(aluno);
    }

    public Aluno removeStudentFromClassroom(Integer id) {
        Aluno aluno = this.findById(id);
        aluno.getSala().removeAluno(aluno);
        return this.repository.save(aluno);
    }

    public List<Aluno> getSemSala() {
        return repository.findAllBySalaIsNull();
    }

    public Aluno putAluno(AlunoPutDto dto) {
        Aluno aluno = findById(dto.getId());

        aluno.setNome(dto.getNome());
        aluno.setRa(dto.getRa());
        aluno.setDtNasc(dto.getDtNasc());

        return repository.save(aluno);
    }
}
