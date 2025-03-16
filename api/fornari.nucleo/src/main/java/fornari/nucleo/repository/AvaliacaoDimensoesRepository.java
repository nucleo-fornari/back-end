package fornari.nucleo.repository;
import fornari.nucleo.domain.entity.AvaliacaoDimensoes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliacaoDimensoesRepository extends JpaRepository<AvaliacaoDimensoes, Integer> {
    List<AvaliacaoDimensoes> findAllByCriadorIdAndTipoDimensaoEquals(Integer userId, String tipo);
}
