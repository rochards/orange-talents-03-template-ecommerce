package br.com.zupacademy.desafiomercadolivre.domain.usuario;

import java.time.OffsetDateTime;

public class UsuarioResponseDTO {

    private Integer id;
    private String login;
    private OffsetDateTime criadoEm;

    public UsuarioResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.login = usuario.getLogin();
        this.criadoEm = usuario.getCriadoEm();
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public OffsetDateTime getCriadoEm() {
        return criadoEm;
    }
}
