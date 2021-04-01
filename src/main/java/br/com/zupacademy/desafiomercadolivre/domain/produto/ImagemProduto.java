package br.com.zupacademy.desafiomercadolivre.domain.produto;

import javax.persistence.*;

@Entity
public class ImagemProduto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String link;

    @ManyToOne(optional = false)
    private Produto produto;

    public ImagemProduto(String link, Produto produto) {
        this.link = link;
        this.produto = produto;
    }
}
