package fornari.nucleo.repository;

import fornari.nucleo.domain.entity.Recado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface RecadoRepository extends JpaRepository<Recado, Integer> {

    @Query("SELECT r FROM Recado r WHERE r.aluno.id = :idAluno")
    List<Recado> findByAluno(Integer idAluno);
}
