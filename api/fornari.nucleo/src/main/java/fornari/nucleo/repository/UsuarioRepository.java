package fornari.nucleo.repository;
import fornari.nucleo.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByCpf(String cpf);

    List<Usuario> findAllBySalaIsNullAndFuncao(String professor);

    List<Usuario> findAllByFuncao(String funcao);
}
