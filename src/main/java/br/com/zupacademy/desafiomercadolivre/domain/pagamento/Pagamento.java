package br.com.zupacademy.desafiomercadolivre.domain.pagamento;

import br.com.zupacademy.desafiomercadolivre.domain.compra.Compra;
import br.com.zupacademy.desafiomercadolivre.domain.pagamento.status.Status;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
public class Pagamento {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    private Compra compra;

    @Column(nullable = false)
    private String pagamentoId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private OffsetDateTime registradoEm;

    @Deprecated
    public Pagamento() {
    }

    public Pagamento(Compra compra, String pagamentoId, Status status) {
        this.compra = compra;
        this.pagamentoId = pagamentoId;
        this.status = status;
        this.registradoEm = OffsetDateTime.now();
    }
}
