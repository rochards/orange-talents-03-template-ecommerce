package br.com.zupacademy.desafiomercadolivre.domain.produto.pergunta;

import br.com.zupacademy.desafiomercadolivre.domain.produto.Produto;
import br.com.zupacademy.desafiomercadolivre.domain.usuario.Usuario;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity(name = "pergunta_produto")
public class Pergunta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String titulo;

    @ManyToOne(optional = false)
    private Usuario usuario;

    @ManyToOne(optional = false)
    private Produto produto;

    @Column(nullable = false)
    private OffsetDateTime criadaEm;

    @Deprecated
    public Pergunta() {
    }

    public Pergunta(String titulo, Usuario usuario, Produto produto) {
        this.titulo = titulo;
        this.usuario = usuario;
        this.produto = produto;
        this.criadaEm = OffsetDateTime.now();
    }

    @Override
    public String toString() {
        return "Pergunta{" +
                "titulo='" + titulo + '\'' +
                ", usuario=" + usuario.getLogin() +
                ", produto=" + produto.getNome() +
                ", criadaEm=" + criadaEm +
                '}';
    }
}
