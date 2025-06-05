package fornari.nucleo.configuration;

import fornari.nucleo.filter.JwtAuthenticationFilter;
import fornari.nucleo.service.UserDetailsServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImp userDetailsServiceImp;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AntPathRequestMatcher[] allowSecretario =  {
            new AntPathRequestMatcher("/usuarios/professor/{id}/sala/remover"),
            new AntPathRequestMatcher("/usuarios/professor/{id}/sala/{salaId}" ),
            new AntPathRequestMatcher("/usuarios/{id}", "DELETE" ),
            new AntPathRequestMatcher("/usuarios/funcionario", "POST" ),
            new AntPathRequestMatcher("/usuarios/{id}", "PUT"),
            new AntPathRequestMatcher("/salas", "POST"),
            new AntPathRequestMatcher("/salas/grupos/{nome}", "POST"),
            new AntPathRequestMatcher("/salas/{id}", "DELETE"),
            new AntPathRequestMatcher("/restricoes/", "POST"),
            new AntPathRequestMatcher("/restricoes/{id}", "DELETE"),
            new AntPathRequestMatcher("/notificacoes/{id}", "DELETE"),
            new AntPathRequestMatcher("/tipos-chamado", "POST"),
            new AntPathRequestMatcher("/tipos-chamado/{id}", "DELETE"),
            new AntPathRequestMatcher("/chamados/{id}", "PATCH"),
            new AntPathRequestMatcher("/alunos", "POST"),
            new AntPathRequestMatcher("/alunos/{id}/responsavel", "PUT"),
            new AntPathRequestMatcher("/alunos", "PUT"),
            new AntPathRequestMatcher("/alunos/{id}", "DELETE"),
            new AntPathRequestMatcher("/alunos/{id}/responsavel/{idResponsavel}", "DELETE"),
            new AntPathRequestMatcher("/alunos/{id}/sala/{salaId}", "PATCH"),
            new AntPathRequestMatcher("/alunos/{id}/sala/remover", "PATCH"),
            new AntPathRequestMatcher("/agendamento/{agendamentoId}/aceitar-ou-modificar", "PUT")
    };

    private final AntPathRequestMatcher[] allowProfessor = {
            new AntPathRequestMatcher("/chamados", "POST"),
            new AntPathRequestMatcher("/avaliacao/pdf/{id}", "GET"),
            new AntPathRequestMatcher("/avaliacao", "POST"),
            new AntPathRequestMatcher("/avaliacao/dimensao", "POST"),
            new AntPathRequestMatcher("/avaliacao/dimensao/{userId}/{tipo}", "GET"),
            new AntPathRequestMatcher("/avaliacao/dimensao/{id}", "DELETE"),
            new AntPathRequestMatcher("/avaliacao/{id}", "DELETE")
    };

    private final AntPathRequestMatcher[] allowResponsavel = {
            new AntPathRequestMatcher("/agendamento/proposta", "POST"),
    };

    private final AntPathRequestMatcher[] allowAll = {
            new AntPathRequestMatcher("/usuarios/login"),
            new AntPathRequestMatcher("/usuarios/esqueci-senha"),
            new AntPathRequestMatcher("/usuarios/token-redefinicao-senha"),
            new AntPathRequestMatcher("/usuarios/redefinir-senha"),
            new AntPathRequestMatcher("/usuarios/{id}/lgpd")
    };

    public SecurityConfig(UserDetailsServiceImp userDetailsServiceImp, JwtAuthenticationFilter authenticationFilter) {
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.jwtAuthenticationFilter = authenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(
                    req -> req.requestMatchers(allowAll).permitAll()
                            .requestMatchers(allowSecretario).hasAuthority("SECRETARIO")
                            .requestMatchers(allowProfessor).hasAuthority("PROFESSOR")
                            .requestMatchers(allowResponsavel).hasAuthority("RESPONSAVEL")
                                .anyRequest()
                                .authenticated()
                ).userDetailsService(userDetailsServiceImp)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuracao = new CorsConfiguration();
        configuracao.applyPermitDefaultValues();
        configuracao.setAllowedMethods(
                Arrays.asList(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.OPTIONS.name(),
                        HttpMethod.HEAD.name(),
                        HttpMethod.TRACE.name()
                )
        );

        configuracao.setExposedHeaders(List.of(HttpHeaders.CONTENT_DISPOSITION));

        UrlBasedCorsConfigurationSource origem = new UrlBasedCorsConfigurationSource();
        origem.registerCorsConfiguration("/**", configuracao);
        return (CorsConfigurationSource) origem;
    }
}
