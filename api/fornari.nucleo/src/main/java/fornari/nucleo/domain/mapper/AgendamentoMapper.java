package fornari.nucleo.domain.mapper;

import fornari.nucleo.domain.dto.agendamento.AgendamentoDTO;
import fornari.nucleo.domain.dto.agendamento.AgendamentoRequestDTO;
import fornari.nucleo.domain.entity.Agendamento;

public class AgendamentoMapper {

    public static AgendamentoRequestDTO toDTO(Agendamento agendamento) {
        if (agendamento == null) return null;

        return AgendamentoRequestDTO.builder()
                .motivo(agendamento.getMotivo())
                .salaId(agendamento.getSala().getId())
                .aceito(agendamento.getAceito())
                .descricao(agendamento.getDescricao())
                .data(agendamento.getData())
                .responsavelId(agendamento.getResponsavel().getId())
                .build();
    }

    public static AgendamentoDTO toDTOwithID(Agendamento agendamento) {
        if (agendamento == null) return null;

        return AgendamentoDTO.builder()
                .id(agendamento.getId())
                .motivo(agendamento.getMotivo())
                .salaId(agendamento.getSala().getId())
                .aceito(agendamento.getAceito())
                .descricao(agendamento.getDescricao())
                .data(agendamento.getData())
                .responsavelNome(agendamento.getResponsavel().getNome())
                .responsavelId(agendamento.getResponsavel().getId())
                .responsavelNome(agendamento.getResponsavel().getNome())
                .build();
    }
}
