package br.com.zupacademy.desafiomercadolivre.config.autenticacao;

import br.com.zupacademy.desafiomercadolivre.domain.usuario.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ImplementsUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public ImplementsUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var optUsuario = usuarioRepository.findByLogin(username);
        if (optUsuario.isEmpty()) {
            throw new UsernameNotFoundException("os dados de login são inválidos");
        }

        var usuario = optUsuario.get();
        return new User(usuario.getLogin(), usuario.getSenha(), new ArrayList<>());
        /* Como por enquanto nao estamos trabalhando com as authorities do usuario, passamos uma collection vazia
        acima. Quando for necessario, a nossa classe Usuario devera implementar a interface UserDetails, e possuir um
        atributo que mapea para uma entidade de perfis/roles/authorities*/
    }
}
