package br.com.zupacademy.desafiomercadolivre.config.autenticacao;

import br.com.zupacademy.desafiomercadolivre.domain.usuario.Usuario;
import br.com.zupacademy.desafiomercadolivre.errors.validators.ExistsValue;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class LoginRequestDTO {

    @NotBlank @Email
    @ExistsValue(message = "não há usuário cadastrado com o e-mail informado", domainClass = Usuario.class,
            fieldName = "login")
    private String login;

    @NotBlank @Length(min = 6)
    private String senha;

    public LoginRequestDTO(@NotBlank @Email String login, @NotBlank @Length(min = 6) String senha) {
        this.login = login;
        this.senha = senha;
    }

    public UsernamePasswordAuthenticationToken toUsernamePasswordAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(login, senha);
    }
}
