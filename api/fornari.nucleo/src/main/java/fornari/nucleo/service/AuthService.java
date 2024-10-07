//package fornari.nucleo.service;
//
//import fornari.nucleo.domain.dto.auth.LoginRequestDto;
//import fornari.nucleo.domain.dto.usuario.UsuarioWithoutPasswordDto;
//import fornari.nucleo.domain.entity.Usuario;
//import fornari.nucleo.helper.messages.ConstMessages;
//import fornari.nucleo.repository.UsuarioRepository;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.Optional;
//
//@Service
//public class AuthService {
//
//    private final UsuarioRepository usuarioRepository;
//
//    public AuthService (UsuarioRepository usuarioRepository) {
//        this.usuarioRepository = usuarioRepository;
//    }
//
//    public UsuarioWithoutPasswordDto login(LoginRequestDto data) {
//
//        Optional<Usuario> opt = null;
//
//        if (data.getEmail() != null) {
//            opt = this.usuarioRepository.findByEmail(data.getEmail());
//        } else if (data.getCpf() != null) {
//            opt = this.usuarioRepository.findByCpf(data.getCpf());
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ConstMessages.NOT_FOUND_EMAIL_AND_CPF);
//        }
//
//        if(opt.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ConstMessages.NOT_FOUND_EMAIL_AND_CPF);
//        }
//
//        Usuario user = opt.get();
//    }
//}
