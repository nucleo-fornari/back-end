package fornari.nucleo.repository;

import fornari.nucleo.domain.entity.SalaGrupo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaGrupoRepository extends JpaRepository<SalaGrupo, Integer> {

    boolean existsByNome(String nome);
}
