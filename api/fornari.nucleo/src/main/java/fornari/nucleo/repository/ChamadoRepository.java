package fornari.nucleo.repository;

import fornari.nucleo.entity.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {
    public List<Chamado> findByFinalizadoEquals(boolean bool);
}
