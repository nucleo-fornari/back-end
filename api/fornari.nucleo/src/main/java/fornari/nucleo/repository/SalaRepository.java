package fornari.nucleo.repository;

import fornari.nucleo.domain.entity.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalaRepository extends JpaRepository<Sala, Integer> {
    Optional<Object> findByNome(String nome);
}
