package fornari.nucleo.repository;

import fornari.nucleo.domain.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {
    public boolean existsByRa(String ra);

    List<Aluno> findAllBySalaIsNull();
}
