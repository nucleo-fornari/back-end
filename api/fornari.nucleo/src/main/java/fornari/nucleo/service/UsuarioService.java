package fornari.nucleo.service;

import fornari.nucleo.configuration.AutenticacaoFilter;
import fornari.nucleo.configuration.GerenciadorTokenJwt;
import fornari.nucleo.configuration.WebSocketHandler;
import fornari.nucleo.domain.dto.usuario.UsuarioTokenDto;
import fornari.nucleo.domain.dto.usuario.UsuarioUpdateRequestDto;
import fornari.nucleo.domain.entity.*;
import fornari.nucleo.domain.mapper.EnderecoMapper;
import fornari.nucleo.domain.mapper.UsuarioMapper;
import fornari.nucleo.helper.Generator;
import fornari.nucleo.helper.messages.ConstMessages;
import fornari.nucleo.helper.validation.GenericValidations;
import fornari.nucleo.repository.ChamadoRepository;
import fornari.nucleo.repository.TokenRedefinicaoSenhaRepository;
import fornari.nucleo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.print.DocFlavor;
import java.net.http.WebSocket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final PasswordEncoder passwordEncoder;
    private final GerenciadorTokenJwt gerenciadorTokenJwt;
    private final AuthenticationManager autenticationManager;
    private final UsuarioRepository repository;
    private final EnderecoService enderecoService;
    private final SalaService salaService;
    private final ChamadoRepository chamadoRepository;
    private final IEmailService emailService;
    private final TokenRedefinicaoSenhaRepository tokenRepository;

    @Transactional
    public Usuario createUsuario(Usuario user) {
        if (!GenericValidations.isValidCpf(user.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ConstMessages.INVALID_CPF);
        }
        String password = Generator.generatePassword();
        user.setSenha(passwordEncoder.encode(password));
        user.setEndereco(this.mapEndereco(user.getEndereco()));
        user.setFiliacoes(new ArrayList<>());

        String[] cc = {};

        this.emailService.sendMail(
                user.getEmail(),
                cc,
                "[ NÚCLEO-FORNARI ] - Você foi cadastrado(a) com sucesso na plataforma!",
                " Sua senha é: " + password + "\n Ela pode ser alterada a qualquer momento através do nosso site ou aplicativo mobile."
        );

        return this.repository.save(user);
    }

    public Endereco mapEndereco(Endereco endereco) {
        return this.enderecoService.findExistentEndereco(endereco)
                .orElseGet(() -> this.enderecoService.create(endereco));
    }

    public List<Usuario> listar() {
        return repository.findAll();
    }

    public Usuario buscarPorID(int id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void delete(Usuario user) {
        chamadoRepository.deleteAll(user.getChamados());

        Endereco endereco = user.getEndereco();
        repository.delete(user);
        if (endereco.getUsuarios().isEmpty()) {
            this.enderecoService.delete(endereco);
        }
    }

    @Transactional
    public Usuario updateUsuario(Usuario data, Integer id) {

        if (!GenericValidations.isValidCpf(data.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ConstMessages.INVALID_CPF);
        }

        Usuario user = this.buscarPorID(id);
        user.setEndereco(this.mapEndereco(data.getEndereco()));
        user.setNome(data.getNome());
        user.setCpf(data.getCpf());
        user.setTelefone(data.getTelefone());
        user.setEmail(data.getEmail());
        user.setDtNasc(data.getDtNasc());
        user.setFuncao(data.getFuncao());

        return repository.save(user);
    }

    public UsuarioTokenDto autenticar(String email, String senha) {
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                email, senha
        );

        final Authentication authentication = this.autenticationManager.authenticate(credentials);

        Usuario usuarioAutenticado =  repository.findByEmail(email).orElseThrow(
                () -> new ResponseStatusException(404, "Email do usuario nao cadastrado", null)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = gerenciadorTokenJwt.generateToken(authentication);
        return UsuarioMapper.toTokenDto(usuarioAutenticado, token);
    }

    public Usuario enrollTeacherWithClassroom(Integer id, Integer idSala) {
        Sala sala = salaService.findById(idSala);
        Usuario user = this.buscarPorID(id);

        if (!Objects.equals(user.getFuncao(), "PROFESSOR")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ConstMessages.USER_NOT_PROFESSOR);
        }

        sala.addProfessor(user);
        return this.repository.save(user);
    }

    public Usuario removeTeacherFromClassroom(Integer id) {
        Usuario user = this.buscarPorID(id);
        user.getSala().removeProfessor(user);
        return this.repository.save(user);
    }

    public List<Usuario> listarProfessoresSemSala() {
        return repository.findAllBySalaIsNullAndFuncao("PROFESSOR");
    }

    @Transactional
    public void esqueciSenha(String email) {
       Usuario usr = repository.findByEmail(email).orElseThrow(
                () -> new ResponseStatusException(404, "Email do usuario nao cadastrado", null));

       String code = Generator.generatePassword();

       TokenRedefinicaoSenha token = new TokenRedefinicaoSenha();
       token.setCode(code);
       token.setValidity(LocalDateTime.now().plusMinutes(15));
       token.setUsuario(usr);

       if(!usr.getTokens().isEmpty()) {
           this.tokenRepository.deleteAll(usr.getTokens());
           usr.setTokens(new ArrayList<>());
       }
       usr.addToken(token);

       this.tokenRepository.save(token);
       this.repository.save(usr);
       String[] cc = {};

       this.emailService.sendMail(
               usr.getEmail(),
               cc,
               "[ NÚCLEO-FORNARI ] - Redefinição de senha",
               " Seu código de redefinição de senha é: " + code
       );
    }

    public void tokenRedefinicaoSenha(String token) {
        List<TokenRedefinicaoSenha> tokens = this.tokenRepository.findByCode(token);

        if (tokens.isEmpty())
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Token inválido");
        if (tokens.get(0).getValidity().isBefore(LocalDateTime.now()))
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "O token enviado expirou. Solicite um novo código.");
    }

    public Usuario findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(
                () -> new ResponseStatusException(404, "Email do usuario nao cadastrado", null));
    }

    @Transactional
    public void redefinirSenha (String senha, String email, String token) {
        if (senha.length() < 8)
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "A senha deve ter no mínimo 8 caracteres");

        if (!senha.matches(".*[^a-zA-Z0-9].*"))
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "A senha deve ter no mínimo 1 caracter especial");

        if (!senha.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).*$"))
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "A senha deve ter caracteres maiúsculos, minúsculos e números");

        this.tokenRedefinicaoSenha(token);

        Usuario usr = findByEmail(email);

        Optional<TokenRedefinicaoSenha> tokenOptional = usr.getTokens().stream()
                .filter(x -> x.getCode().equals(token))
                .findAny();

        if(tokenOptional.isEmpty())
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "O token informado não pertence ao usuario do email informado.");

        TokenRedefinicaoSenha tk = tokenOptional.get();
        usr.removeToken(tk);
        this.tokenRepository.delete(tk);
        usr.setSenha(passwordEncoder.encode(senha));
        this.repository.save(usr);
    }
}
