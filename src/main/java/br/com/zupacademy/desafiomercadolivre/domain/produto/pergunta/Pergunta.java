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
    private Usuario interessado;

    @ManyToOne(optional = false)
    private Produto produto;

    @Column(nullable = false)
    private OffsetDateTime criadaEm;

    @Deprecated
    public Pergunta() {
    }

    public Pergunta(String titulo, Usuario interessado, Produto produto) {
        this.titulo = titulo;
        this.interessado = interessado;
        this.produto = produto;
        this.criadaEm = OffsetDateTime.now();
    }

    public String getTitulo() {
        return titulo;
    }

    public Usuario getInteressado() {
        return interessado;
    }

    public Produto getProduto() {
        return produto;
    }

    public OffsetDateTime getCriadaEm() {
        return criadaEm;
    }
}
