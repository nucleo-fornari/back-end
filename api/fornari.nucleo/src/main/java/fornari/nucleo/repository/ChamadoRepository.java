package fornari.nucleo.repository;

import fornari.nucleo.domain.entity.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {
    List<Chamado> findByFinalizadoEquals(boolean bool);
    List<Chamado> findByUsuarioId(Integer id);
}
