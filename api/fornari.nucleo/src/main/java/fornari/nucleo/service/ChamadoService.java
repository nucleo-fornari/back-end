package fornari.nucleo.service;

import fornari.nucleo.dto.ChamadoDto;
import fornari.nucleo.entity.Chamado;
import fornari.nucleo.repository.ChamadoRepository;
import fornari.nucleo.repository.ChamadoTipoRepository;
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
    private ChamadoTipoService chamadoTipoService;

    @Autowired
    private ChamadoTipoRepository chamadoTipoRepository;
    public List<ChamadoDto> findByFinalizadoEqualsFalse() {
        List<Chamado> list = this.repository.findByFinalizadoEquals(false);

        if (list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        return this.mapMultipleChamadoToChamadoDto(list);
    }

    private List<Chamado> mapMultipleChamadoDtoToChamado(List<ChamadoDto> listDto) {
        List<Chamado> list = new  ArrayList<>();

        for (ChamadoDto dto : listDto) {
            list.add(this.mapChamadoDtoToChamado(dto));
        }

        return list;
    }

    private Chamado mapChamadoDtoToChamado(ChamadoDto dto) {
        Chamado chamado = dto.getId() != null ? repository.findById(dto.getId()).get() : new Chamado();

        chamado.setId(dto.getId());
        chamado.setDescricao(dto.getDescricao());
        chamado.setFinalizado(dto.isFinalizado());
        chamado.setDtAbertura(dto.getDtAbertura());
        chamado.setDtFechamento(dto.getDtFechamento());
        chamado.setCriancaAtipica(dto.isCriancaAtipica());
        chamado.setTipo(this.chamadoTipoRepository.findById(dto.getTipo().getId()).get());

        if (dto.getId() == null) {
            chamado.setDtAbertura(LocalDateTime.now());
            chamado.setFinalizado(false);
        }

        return chamado;
    }

    private List<ChamadoDto> mapMultipleChamadoToChamadoDto(List<Chamado> list) {
        List<ChamadoDto> listDto = new ArrayList<>();
        for (Chamado chamado : list) {
            listDto.add(this.mapChamadoToChamadoDto(chamado));
        }

        return listDto;
    }

    private ChamadoDto mapChamadoToChamadoDto(Chamado chamado) {
        ChamadoDto dto = new ChamadoDto();

        dto.setFinalizado(chamado.isFinalizado());
        dto.setDescricao(chamado.getDescricao());
        dto.setId(chamado.getId());
        dto.setDtAbertura(chamado.getDtAbertura());
        dto.setDtFechamento(chamado.getDtFechamento());
        dto.setCriancaAtipica(chamado.isCriancaAtipica());
        dto.setTipo(this.chamadoTipoService.mapChamadoTipoToChamadoTipoDto(chamado.getTipo()));

        return dto;
    }

    public ChamadoDto create(ChamadoDto dto) {
        dto.setId(null);
        return this.createOrUpdate(dto);
    }

    private ChamadoDto createOrUpdate(ChamadoDto dto) {
        return this.mapChamadoToChamadoDto(
                this.repository.save(
                        this.mapChamadoDtoToChamado(dto)));
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
