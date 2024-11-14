package fornari.nucleo.service;

import fornari.nucleo.domain.entity.Aluno;
import fornari.nucleo.domain.entity.Recado;
import fornari.nucleo.repository.RecadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecadoService {

    private final RecadoRepository repository;
    private final AlunoService alunoService;

    public Recado create(Recado recado, Integer idAluno) {
        Aluno aluno = this.alunoService.findById(idAluno);
        recado.setAluno(aluno);
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
}
