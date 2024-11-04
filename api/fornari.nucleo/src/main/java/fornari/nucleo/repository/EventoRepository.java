package fornari.nucleo.repository;

import fornari.nucleo.domain.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
}
