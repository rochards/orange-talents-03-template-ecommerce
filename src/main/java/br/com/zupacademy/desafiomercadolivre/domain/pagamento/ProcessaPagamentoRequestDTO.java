package br.com.zupacademy.desafiomercadolivre.domain.pagamento;

import br.com.zupacademy.desafiomercadolivre.domain.compra.Compra;
import br.com.zupacademy.desafiomercadolivre.domain.pagamento.status.Status;
import br.com.zupacademy.desafiomercadolivre.errors.validators.ExistsValue;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProcessaPagamentoRequestDTO {

    @NotNull
    @ExistsValue(message = "não há registro de compra para esse id", domainClass = Compra.class, fieldName = "id")
    private Integer compraId;

    @NotBlank
    private String pagamentoId;

    @NotNull
    private Status status;

    public ProcessaPagamentoRequestDTO(@NotNull Integer compraId, @NotBlank String pagamentoId, @NotNull Status status) {
        this.compraId = compraId;
        this.pagamentoId = pagamentoId;
        this.status = status;
    }

    public Pagamento toModel(EntityManager em) {
        // Eh esperado que compraId exista no banco, pois ja foi feita uma validacao acima
        var compra = em.find(Compra.class, compraId);
        return new Pagamento(compra, pagamentoId, status);
    }

    public Integer getCompraId() {
        return compraId;
    }

    public boolean pagoComSucesso() {
        return status == Status.SUCESSO;
    }
}
