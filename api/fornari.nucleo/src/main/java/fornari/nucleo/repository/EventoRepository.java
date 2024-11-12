package fornari.nucleo.repository;

import fornari.nucleo.domain.entity.Evento;
import fornari.nucleo.domain.entity.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
    List<Evento> findAllByTipo(String tipo);
    List<Evento> findAllBySalasContains(Sala sala);
}
