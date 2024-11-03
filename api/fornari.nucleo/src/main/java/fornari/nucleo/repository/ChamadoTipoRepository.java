package fornari.nucleo.repository;

import fornari.nucleo.domain.entity.ChamadoTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChamadoTipoRepository extends JpaRepository<ChamadoTipo, Integer> {
    @Query("SELECT ct FROM ChamadoTipo ct ORDER BY ct.id")
    List<ChamadoTipo> findAllOrderById();
}
