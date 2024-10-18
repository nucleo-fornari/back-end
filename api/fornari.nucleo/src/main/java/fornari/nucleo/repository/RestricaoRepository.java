package fornari.nucleo.repository;

import fornari.nucleo.domain.entity.Restricao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestricaoRepository extends JpaRepository<Restricao, Integer> {
    public boolean existsByTipo(String tipo);

    @Override
    Optional<Restricao> findById(Integer integer);
}
