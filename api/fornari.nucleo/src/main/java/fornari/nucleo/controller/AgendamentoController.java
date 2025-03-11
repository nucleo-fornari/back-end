package fornari.nucleo.controller;

import fornari.nucleo.domain.dto.agendamento.AgendamentoDTO;
import fornari.nucleo.domain.dto.agendamento.AgendamentoRequestDTO;
import fornari.nucleo.domain.entity.Agendamento;
import fornari.nucleo.domain.entity.Sala;
import fornari.nucleo.domain.entity.Usuario;
import fornari.nucleo.domain.mapper.AgendamentoMapper;
import fornari.nucleo.domain.mapper.NotificacaoMapper;
import fornari.nucleo.service.AgendamentoService;
import fornari.nucleo.service.NotificacaoService;
import fornari.nucleo.service.SalaService;
import fornari.nucleo.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamento")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;
    private final UsuarioService usuarioService;
    private final SalaService salaService;
    private final NotificacaoService notificacaoService;

    @GetMapping
    public ResponseEntity<List<AgendamentoDTO>> getAll() {
        List<Agendamento> agendamentos = agendamentoService.listar();

        if (agendamentos.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(
                agendamentos.stream().map(AgendamentoMapper::toDTOwithID).toList()
        );
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<AgendamentoDTO>> getByUsuario(@PathVariable Integer usuarioId) {
        List<Agendamento> agendamentos = agendamentoService.buscarPorIdUser(usuarioId);

        if (agendamentos.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(
                agendamentos.stream().map(AgendamentoMapper::toDTOwithID).toList()
        );
    }

    @GetMapping("/{idSala}")
    public ResponseEntity<List<AgendamentoDTO>> getBySala(@PathVariable Integer idSala) {
        List<Agendamento> agendamentos = agendamentoService.buscarPorIdSala(idSala);

        if (agendamentos.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(
                agendamentos.stream().map(AgendamentoMapper::toDTOwithID).toList()
        );
    }

    @PostMapping("/proposta")
    public ResponseEntity<AgendamentoRequestDTO> criarProposta(@RequestBody AgendamentoRequestDTO agendamentoRequestDTO) {
        Agendamento agendamento = new Agendamento();
        Usuario responsavel = usuarioService.buscarPorID(agendamentoRequestDTO.getResponsavelId());
        Sala sala = salaService.findById(agendamentoRequestDTO.getSalaId());

        agendamento.setMotivo(agendamentoRequestDTO.getMotivo());
        agendamento.setDescricao(agendamentoRequestDTO.getDescricao());
        agendamento.setData(agendamentoRequestDTO.getData());
        agendamento.setAceito(false);
        agendamento.setResponsavel(responsavel);
        agendamento.setSala(sala);

        AgendamentoRequestDTO agendamentoCriado = AgendamentoMapper.toDTO(agendamentoService.salvar(agendamento));

        notificacaoService.criarNotificacao("REUNIAO", "Uma proposta de reunião foi criado!", sala.getProfessores()
                .stream().map(professor -> professor.getId()).toList());

        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoCriado);
    }

    @PutMapping("/{agendamentoId}/aceitar-ou-modificar")
    public ResponseEntity<AgendamentoRequestDTO> aceitarOuModificarData(
            @PathVariable Integer agendamentoId,
            @RequestBody AgendamentoRequestDTO agendamentoRequestDTO) {

        Agendamento agendamento = agendamentoService.buscarPorId(agendamentoId);

        if (agendamento == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
            agendamento.setAceito(true);
            agendamento.setData(agendamentoRequestDTO.getData());
            AgendamentoRequestDTO agendamentoAtualizado = AgendamentoMapper.toDTO(agendamentoService.salvar(agendamento));

            notificacaoService.criarNotificacao(
                    "REUNIAO", "Sua proposta de reunião foi aceita!", agendamentoAtualizado.getResponsavelId()
            );

            return ResponseEntity.ok(agendamentoAtualizado);
    }
}

