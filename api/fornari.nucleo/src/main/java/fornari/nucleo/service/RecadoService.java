package fornari.nucleo.service;

import fornari.nucleo.domain.entity.Aluno;
import fornari.nucleo.domain.entity.Recado;
import fornari.nucleo.domain.entity.Usuario;
import fornari.nucleo.helper.messages.ConstMessages;
import fornari.nucleo.repository.RecadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecadoService {

    private final RecadoRepository repository;
    private final AlunoService alunoService;
    private final UsuarioService usuarioService;

    public Recado create(Recado recado, Integer idAluno, Integer usuarioId) {
        Aluno aluno = this.alunoService.findById(idAluno);
        recado.setAluno(aluno);
        Usuario user = usuarioService.buscarPorID(usuarioId);
        recado.setResponsavel(user);
        user.getRecados().add(recado);
        recado.setDtCriacao(LocalDateTime.now());
        return this.repository.save(recado);
    }

    public List<Recado> findAll() {
        return this.repository.findAll();
    }

    public List<Recado> findByAluno(Integer idAluno) {
        Aluno aluno = this.alunoService.findById(idAluno);
        return aluno.getRecados() == null ? null : aluno.getRecados();
    }

    public Recado findById(Integer id) {
        return this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ConstMessages.NOT_FOUND_RECADO));
    }

    public void delete(Integer id) {
        this.repository.delete(this.findById(id));
    }

    public List<Recado> findByUserId(Integer id) {
        return this.repository.findByResponsavel_Id(id);
    }
}
