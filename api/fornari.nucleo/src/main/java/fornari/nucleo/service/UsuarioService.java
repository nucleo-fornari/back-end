package fornari.nucleo.service;

import fornari.nucleo.configuration.AutenticacaoFilter;
import fornari.nucleo.configuration.GerenciadorTokenJwt;
import fornari.nucleo.domain.dto.usuario.UsuarioTokenDto;
import fornari.nucleo.domain.dto.usuario.UsuarioUpdateRequestDto;
import fornari.nucleo.domain.entity.Endereco;
import fornari.nucleo.domain.entity.Usuario;
import fornari.nucleo.domain.mapper.EnderecoMapper;
import fornari.nucleo.domain.mapper.UsuarioMapper;
import fornari.nucleo.helper.Generator;
import fornari.nucleo.helper.messages.ConstMessages;
import fornari.nucleo.helper.validation.GenericValidations;
import fornari.nucleo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final PasswordEncoder passwordEncoder;
    private final GerenciadorTokenJwt gerenciadorTokenJwt;
    private final AuthenticationManager autenticationManager;
    private final UsuarioRepository repository;
    private final EnderecoService enderecoService;

    @Transactional
    public Usuario createUsuario(Usuario user) {
        //user.setSenha(passwordEncoder.encode(Generator.generatePassword()));
        System.out.println("\n \n \n \n \n ENTROU NO CREATE \n \n \n \n \n \n \n \n");
        if (!GenericValidations.isValidCpf(user.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ConstMessages.INVALID_CPF);
        }
        user.setSenha(passwordEncoder.encode("12345678"));
        user.setEndereco(this.mapEndereco(user.getEndereco()));
        user.setFiliacoes(new ArrayList<>());
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
}
