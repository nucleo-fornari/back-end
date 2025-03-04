package fornari.nucleo.repository;

import fornari.nucleo.domain.entity.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer>{
    List<Agendamento> findAllByResponsavelId(Integer id);

    List<Agendamento> findAllBySalaId(Integer id);
}
