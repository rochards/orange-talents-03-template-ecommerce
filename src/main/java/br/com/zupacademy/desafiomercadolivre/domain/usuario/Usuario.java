package br.com.zupacademy.desafiomercadolivre.domain.usuario;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private OffsetDateTime criadoEm;

    @Deprecated
    public Usuario() {
    }

    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
        this.criadoEm = OffsetDateTime.now();
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
