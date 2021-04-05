package br.com.zupacademy.desafiomercadolivre.domain.compra;

import br.com.zupacademy.desafiomercadolivre.domain.compra.pagamento.FormaPagamento;
import br.com.zupacademy.desafiomercadolivre.domain.compra.pagamento.GatewayPagamento;
import br.com.zupacademy.desafiomercadolivre.domain.compra.status.Status;
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
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    @ManyToOne(optional = false)
    private Produto produto;

    @ManyToOne(optional = false)
    private Usuario comprador;

    @Deprecated
    public Compra() {
    }

    public Compra(BigDecimal valorItem, Integer quantidade, FormaPagamento formaPagamento, Produto produto, Usuario comprador) {
        this.valorItem = valorItem;
        this.quantidade = quantidade;
        this.status = Status.INICIADA;
        this.formaPagamento = formaPagamento;
        this.produto = produto;
        this.comprador = comprador;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String enviaRegistroCompraParaGatewayPagamento() {
        return this.formaPagamento.getGatewayPagamento().enviaRegistroDeCompra(id, "https://localhost/api" +
                "/recebe_conta_paga");
    }
}
