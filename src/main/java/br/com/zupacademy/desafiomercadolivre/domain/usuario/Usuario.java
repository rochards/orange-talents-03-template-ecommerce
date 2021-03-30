package br.com.zupacademy.desafiomercadolivre.domain.usuario;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    /**
     * @param login deve ser um e-mail no formato adequado
     * @param senha nao deve vir criptografada, pois esse passo eh feito dentro do construtor
     * */
    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = new BCryptPasswordEncoder().encode(senha);
        this.criadoEm = OffsetDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public OffsetDateTime getCriadoEm() {
        return criadoEm;
    }
}
