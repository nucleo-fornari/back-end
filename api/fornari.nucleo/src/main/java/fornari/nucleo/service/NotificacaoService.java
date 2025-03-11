package fornari.nucleo.service;

import fornari.nucleo.configuration.WebSocketHandler;
import fornari.nucleo.domain.dto.NotificacaoDTO;
import fornari.nucleo.domain.entity.Notificacao;
import fornari.nucleo.domain.entity.Usuario;
import fornari.nucleo.repository.NotificacaoRepository;
import fornari.nucleo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final UsuarioRepository usuarioRepository;

    @Lazy
    private final WebSocketHandler webSocketHandler;

    public void criarNotificacaoParaSecretaria(String titulo, String mensagem) {
        List<Usuario> ids = usuarioRepository.findAllByFuncao("SECRETARIO");
        criarNotificacao(titulo, mensagem, ids.stream().map(Usuario::getId).toList());
    }

    public void criarNotificacao(String titulo, String mensagem, Integer usuarioID) {
        NotificacaoDTO notificacaoDTO = new NotificacaoDTO(titulo, mensagem, usuarioID);
        webSocketHandler.enviarOuSalvarNotificacao(usuarioID, notificacaoDTO);
    }

    public void criarNotificacao(String titulo, String mensagem, List<Integer> listIdUser) {
        NotificacaoDTO notificacaoDTO = new NotificacaoDTO(titulo, mensagem, null);
        webSocketHandler.enviarOuSalvarNotificacao(listIdUser, notificacaoDTO);
    }

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