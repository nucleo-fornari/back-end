//package fornari.nucleo.controller;
//
//import fornari.nucleo.domain.dto.auth.LoginRequestDto;
//import fornari.nucleo.domain.dto.usuario.UsuarioWithoutPasswordDto;
//import fornari.nucleo.service.AuthService;
//import org.apache.coyote.Response;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    private final AuthService service;
//
//    public AuthController(AuthService service) {
//        this.service = service;
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<UsuarioWithoutPasswordDto> login(@RequestBody LoginRequestDto data) {
//        this.service.login(data);
//    }
//}
