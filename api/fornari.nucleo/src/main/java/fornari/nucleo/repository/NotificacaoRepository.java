package fornari.nucleo.repository;

import fornari.nucleo.domain.entity.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer> {
    List<Notificacao> findByUsuarioIdAndLidaFalse(Integer usuarioId);
    List<Notificacao> findByUsuarioId(Integer usuarioId);
}