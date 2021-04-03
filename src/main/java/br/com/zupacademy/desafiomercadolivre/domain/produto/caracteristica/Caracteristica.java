package br.com.zupacademy.desafiomercadolivre.domain.produto.caracteristica;

import br.com.zupacademy.desafiomercadolivre.domain.produto.Produto;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Caracteristica {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, length = 1000)
    private String descricao;

    @ManyToOne(optional = false)
    private Produto produto;

    @Deprecated
    public Caracteristica() {
    }

    public Caracteristica(String nome, String descricao, Produto produto) {
        this.nome = nome;
        this.descricao = descricao;
        this.produto = produto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Caracteristica)) return false;
        Caracteristica that = (Caracteristica) o;
        return nome.equals(that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}
