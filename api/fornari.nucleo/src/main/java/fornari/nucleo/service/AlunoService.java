package fornari.nucleo.service;

import fornari.nucleo.domain.entity.Aluno;
import fornari.nucleo.domain.entity.Filiacao;
import fornari.nucleo.domain.entity.Usuario;
import fornari.nucleo.repository.AlunoRepository;
import fornari.nucleo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlunoService {

    private final AlunoRepository repository;
    private final UsuarioService usuarioService;

    private final UsuarioRepository usuarioRepository;

    public AlunoService(AlunoRepository repository, UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    public Aluno create(Aluno aluno) {

        Aluno aluno1 = this.repository.save(aluno);

        for (Filiacao x : aluno1.getFiliacoes()) {
            Optional<Usuario> responsavel = this.usuarioRepository.findByCpf(x.getResponsavel().getCpf());
            if (responsavel.isPresent()) {
                x.setResponsavel(this.usuarioService.updateUsuario(x.getResponsavel(), responsavel.get().getId()));
            }
            x.setResponsavel(this.usuarioService.createUsuario(x.getResponsavel()));
        }

        return aluno1;
    }
}
