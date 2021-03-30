package br.com.zupacademy.desafiomercadolivre.config.security;

import br.com.zupacademy.desafiomercadolivre.domain.usuario.Usuario;
import br.com.zupacademy.desafiomercadolivre.domain.usuario.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/* Classe criada para receber a requisicao do cliente com o token, fazer a verificacao e validacao desse token. A
cada requisicao do cliente, esse filtro a intercepta. Tal configuracao eh realizada na classe SecurityConfiguration.*/
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;

    public JwtRequestFilter(JwtUtil jwtUtil, UsuarioRepository usuarioRepository) {
        this.jwtUtil = jwtUtil;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        String token = recuperaToken(request);
        boolean tokenEhValido = jwtUtil.isTokenValido(token);

        if (tokenEhValido) {
            autenticaUsuario(token);
        }

        filterChain.doFilter(request, response);
    }

    private void autenticaUsuario(String token) {

        String loginUsuario = jwtUtil.getLoginUsuario(token);
        Optional<Usuario> optUsuario = usuarioRepository.findByLogin(loginUsuario);
        if (optUsuario.isPresent()) {
            var usuario = optUsuario.get();
            var usuarioAutenticado = new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha(),
                    new ArrayList<>());

            SecurityContextHolder.getContext().setAuthentication(usuarioAutenticado);
        }
    }

    private String recuperaToken(HttpServletRequest request) {
        // Eh esperado que o token venha no header Authorization do http
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer")) {
            return null;
        }

        return token.substring("Bearer ".length());
    }
}
