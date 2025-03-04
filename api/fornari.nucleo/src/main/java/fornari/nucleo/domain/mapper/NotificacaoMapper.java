package fornari.nucleo.domain.mapper;

import fornari.nucleo.domain.dto.NotificacaoDTO;
import fornari.nucleo.domain.entity.Notificacao;

public class NotificacaoMapper {
    public static NotificacaoDTO toDTO(Notificacao notificacao) {
        if (notificacao == null) return null;

        return NotificacaoDTO.builder()
                .titulo(notificacao.getTitulo())
                .mensagem(notificacao.getMensagem())
                .dataCriacao(notificacao.getDataCriacao())
                .lida(notificacao.isLida())
                .build();
    }
}
