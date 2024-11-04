package fornari.nucleo.service;

import fornari.nucleo.domain.dto.ChamadoTipoDto;
import fornari.nucleo.domain.entity.Chamado;
import fornari.nucleo.domain.entity.ChamadoTipo;
import fornari.nucleo.domain.mapper.ChamadoTipoMapper;
import fornari.nucleo.helper.messages.ConstMessages;
import fornari.nucleo.repository.ChamadoTipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ChamadoTipoService {
    
    @Autowired 
    private ChamadoTipoRepository repository;

    public ChamadoTipo create(ChamadoTipoDto dto) {
        dto.setId(null);
        return repository.save(ChamadoTipoMapper.toChamadoTipo(dto));
    }

    //IMPLEMENTAÇÃO PESQUISA BINÁRIA
    public ChamadoTipo findById(Integer id) {
        List<ChamadoTipo> list = this.repository.findAllOrderById();

        int x = list.size() - 1; //índice superior
        int z = 0; //índice inferior
        int y; //indice do meio

        while (z <= x) {
            y = (z + x) / 2;
            if (list.get(y).getId().equals(id)) {
                return list.get(y);
            } else if(id < list.get(y).getId()) {
                x = y - 1;
            } else {
                z = y + 1;
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                ConstMessages.NOT_FOUND_CHAMADO_TIPO_BY_ID.formatted(id));
    }


    public List<ChamadoTipo> listar() {
        return this.repository.findAll();
    }
}
