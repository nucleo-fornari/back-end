package fornari.nucleo.repository;
import fornari.nucleo.domain.entity.AlunoAvaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface AlunoAvaliacaoRepository extends JpaRepository<AlunoAvaliacao, Integer> {
    boolean existsByDtCriacaoBetween(LocalDate startDate, LocalDate endDate);
}
