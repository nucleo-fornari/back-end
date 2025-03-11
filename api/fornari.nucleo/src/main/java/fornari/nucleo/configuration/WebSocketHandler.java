package fornari.nucleo.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fornari.nucleo.domain.dto.NotificacaoDTO;
import fornari.nucleo.domain.entity.Notificacao;
import fornari.nucleo.domain.mapper.NotificacaoMapper;
import fornari.nucleo.service.NotificacaoService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final ConcurrentHashMap<Integer, WebSocketSession> usuariosConectados = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;
    private final NotificacaoService notificacaoService;

    public WebSocketHandler(@Lazy NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Integer usuarioId = extrairUsuarioId(session);
        if (usuarioId != null) {
            usuariosConectados.put(usuarioId, session);
            List<Notificacao> notificacoesPendentes = notificacaoService.buscarNotificacoesPendentes(usuarioId);
            notificacoesPendentes.forEach(n -> enviarNotificacao(usuarioId, NotificacaoMapper.toDTO(n)));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Integer usuarioId = extrairUsuarioId(session);
        if (usuarioId != null) {
            usuariosConectados.remove(usuarioId);
        }
    }

    public boolean estaConectado(Integer usuarioId) {
        return usuariosConectados.containsKey(usuarioId);
    }

    public void enviarOuSalvarNotificacao(Integer usuarioId, NotificacaoDTO notificacao) {
        if (estaConectado(usuarioId)) {
                enviarNotificacao(usuarioId, notificacao);
        } else {
            Notificacao notificacao1 = NotificacaoMapper.to(notificacao);
            notificacaoService.salvarNotificacao(usuarioId, notificacao1);
        }
    }

    public void enviarOuSalvarNotificacao(List<Integer> listId, NotificacaoDTO notificacao) {
        for (Integer usuarioId : listId) {
            enviarOuSalvarNotificacao(usuarioId, notificacao);
        }
    }

    private void enviarNotificacao(Integer usuarioId, NotificacaoDTO notificacao) {
        WebSocketSession session = usuariosConectados.get(usuarioId);
        if (session != null && session.isOpen()) {
            synchronized (session) {
                try {
                    session.sendMessage(
                            new TextMessage(
                                    objectMapper.writeValueAsString(
                                            notificacao
                                    )
                            )
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Integer extrairUsuarioId(WebSocketSession session) {
        String query = session.getUri().getQuery();
        if (query != null && query.startsWith("usuarioId=")) {
            try {
                return Integer.parseInt(query.split("=")[1]);
            } catch (NumberFormatException e) {
                System.err.println("ID do usuário inválido: " + query.split("=")[1]);
                return null;
            }
        }
        return null;
    }
}
