package br.com.zupacademy.desafiomercadolivre.config.security;

import br.com.zupacademy.desafiomercadolivre.domain.usuario.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final ImplementsUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;

    public SecurityConfiguration(ImplementsUserDetailsService userDetailsService, JwtUtil jwtUtil, UsuarioRepository usuarioRepository) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.usuarioRepository = usuarioRepository;
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        /*
         * Foi necessario sobrescrever esse metodo para ele me devolver uma instancia de AuthenticationManager, pois
         * o Spring por si so, nao consegue injetar essa dependencia. Tal dependencia eh necessaria la no
         * LoginController.
         * */
        return super.authenticationManager();
    }

    @Override // Eh nesse metodo que configuramos a parte de autenticacao do usuario
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override // Aqui configuramos as autorizacoes
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/usuarios").permitAll()
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new JwtRequestFilter(jwtUtil, usuarioRepository), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();
        /** Eu preciso desabilitar o csrf para o POST funcionar, pq ele nao eh Idempotente, ou seja, ele modifica o
         * estado da aplicacao servidora.
         * SPRING DOC -> Our recommendation is to use CSRF protection for any request that could be processed by a
         * browser by normal users. If you are only creating a service that is used by non-browser clients,
         * you will likely want to disable CSRF protection */
    }
}
