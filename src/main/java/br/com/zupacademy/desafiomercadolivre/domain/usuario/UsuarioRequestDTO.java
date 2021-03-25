package br.com.zupacademy.desafiomercadolivre.domain.usuario;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UsuarioRequestDTO {

    @NotBlank @Email
    private String login;

    @NotBlank @Length(min = 6)
    private String senha;

    public UsuarioRequestDTO(@NotBlank @Email String login, @NotBlank @Length(min = 6) String senha) {
        this.login = login;
        this.senha = senha;
    }

    public Usuario toModel() {
        return new Usuario(login, senha);
    }
}
