package fornari.nucleo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fornari.nucleo.domain.entity.TokenRedefinicaoSenha;

import java.util.List;

public interface TokenRedefinicaoSenhaRepository extends JpaRepository<TokenRedefinicaoSenha, Integer> {
    List<TokenRedefinicaoSenha> findByCode(String code);
}
