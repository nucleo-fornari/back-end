package fornari.nucleo.service;

import fornari.nucleo.domain.entity.Notificacao;
import fornari.nucleo.repository.NotificacaoRepository;
import fornari.nucleo.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final UsuarioRepository usuarioRepository;

    public void salvarNotificacao(Integer usuarioId, Notificacao notificacao) {
        notificacao.setUsuario(usuarioRepository.findById(usuarioId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario não existe")
        ));
        notificacaoRepository.save(notificacao);
    }

    public List<Notificacao> buscarNotificacoesPendentes(Integer usuarioId) {
        return notificacaoRepository.findByUsuarioIdAndLidaFalse(usuarioId);
    }

    public void removerNotificacoes(Integer usuarioId) {
        List<Notificacao> notificacoes = notificacaoRepository.findByUsuarioId(usuarioId);
        notificacaoRepository.deleteAll(notificacoes);
    }
}