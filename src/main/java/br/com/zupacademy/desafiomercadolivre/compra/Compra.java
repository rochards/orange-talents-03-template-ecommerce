package br.com.zupacademy.desafiomercadolivre.compra;

import br.com.zupacademy.desafiomercadolivre.domain.produto.Produto;
import br.com.zupacademy.desafiomercadolivre.domain.usuario.Usuario;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Compra {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private BigDecimal valorItem;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private String formaPagamento;

    @ManyToOne(optional = false)
    private Produto produto;

    @ManyToOne(optional = false)
    private Usuario comprador;

    @Deprecated
    public Compra() {
    }

    public Compra(BigDecimal valorItem, Integer quantidade, String formaPagamento, Produto produto, Usuario comprador) {
        this.valorItem = valorItem;
        this.quantidade = quantidade;
        this.formaPagamento = formaPagamento;
        this.produto = produto;
        this.comprador = comprador;
    }
}
