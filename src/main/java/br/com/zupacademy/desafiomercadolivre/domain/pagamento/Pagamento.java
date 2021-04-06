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

    @Column(nullable = false, unique = true)
    private String transacaoId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private OffsetDateTime registradoEm;

    @Deprecated
    public Pagamento() {
    }

    public Pagamento(Compra compra, String transacaoId, Status status) {
        this.compra = compra;
        this.transacaoId = transacaoId;
        this.status = status;
        this.registradoEm = OffsetDateTime.now();
    }

    public Status getStatus() {
        return status;
    }
}
