package br.com.zupacademy.desafiomercadolivre.domain.produto.imagem;

import br.com.zupacademy.desafiomercadolivre.domain.produto.Produto;

import javax.persistence.*;

@Entity
public class Imagem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String link;

    @ManyToOne(optional = false)
    private Produto produto;

    @Deprecated
    public Imagem() {
    }

    public Imagem(String link, Produto produto) {
        this.link = link;
        this.produto = produto;
    }
}
