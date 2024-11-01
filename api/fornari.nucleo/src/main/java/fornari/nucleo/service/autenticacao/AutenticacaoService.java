package fornari.nucleo.service.autenticacao;

import fornari.nucleo.domain.dto.usuario.UsuarioDetalhesDto;
import fornari.nucleo.domain.entity.Usuario;
import fornari.nucleo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UsuarioDetalhesDto(
                usuarioRepository.findByEmail(username).orElseThrow(
                        () ->  new UsernameNotFoundException(String.format("usuario: %s nao encontrado", username))
                )
        );
    }
}
