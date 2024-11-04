package fornari.nucleo.service;

import fornari.nucleo.domain.dto.ChamadoDto;
import fornari.nucleo.domain.entity.Chamado;
import fornari.nucleo.domain.entity.ChamadoTipo;
import fornari.nucleo.domain.mapper.ChamadoMapper;
import fornari.nucleo.helper.messages.ConstMessages;
import fornari.nucleo.repository.ChamadoRepository;
import fornari.nucleo.repository.ChamadoTipoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChamadoService {
    @Autowired
    private ChamadoRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ChamadoTipoService chamadoTipoService;

    public List<Chamado> findByFinalizadoEqualsFalse() {
        List<Chamado> list = this.repository.findByFinalizadoEquals(false);

        if (list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        return list;
    }

    public Chamado create(ChamadoDto dto, Integer idUsuario) {
        dto.setId(null);

        if (dto.getTipo().getId() == null) {
            throw new ValidationException(ConstMessages.NOT_ALLOWED_TO_REGISTER_CHAMADO_WITHOUT_TIPO);
        }

        ChamadoTipo chamadoTipo = this.chamadoTipoService.findById(dto.getTipo().getId());
        Chamado chamado = ChamadoMapper.toChamado(dto);
        chamado.setTipo(chamadoTipo);
        chamado.setResponsavel(this.usuarioService.buscarPorID(idUsuario));
        chamado.setDtAbertura(LocalDateTime.now());
        return this.repository.save(chamado);
    }

    public void finish(Integer id) {
        Optional<Chamado> chamado = this.repository.findById(id);

        if(chamado.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        chamado.get().setFinalizado(true);
        this.repository.save(chamado.get());
    }
}
