package fornari.nucleo.controller;

import fornari.nucleo.domain.entity.Notificacao;
import fornari.nucleo.service.NotificacaoService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notificacoes")
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<Notificacao>> buscarNotificacoes(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(
                notificacaoService.buscarNotificacoesPendentes(usuarioId)
        );
    }
}
