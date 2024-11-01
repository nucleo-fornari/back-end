package fornari.nucleo.repository;

import fornari.nucleo.domain.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    boolean existsByCepAndNumeroAndComplemento(String cep, String numero, String complemento);
    Optional<Endereco> findOneByCepAndNumeroAndComplemento(String cep, String numero, String complemento);
}
