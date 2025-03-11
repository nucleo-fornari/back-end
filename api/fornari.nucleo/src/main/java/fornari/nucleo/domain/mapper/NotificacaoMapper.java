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
                .usuarioId(notificacao.getUsuario().getId())
                .build();
    }

    public static Notificacao to(NotificacaoDTO dto) {
        if (dto == null) return null;

        return Notificacao.builder()
                .lida(dto.isLida())
                .dataCriacao(dto.getDataCriacao())
                .mensagem(dto.getMensagem())
                .titulo(dto.getTitulo())
                .build();
    }
}
