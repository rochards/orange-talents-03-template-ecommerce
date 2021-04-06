package br.com.zupacademy.desafiomercadolivre.domain.pagamento;

import br.com.zupacademy.desafiomercadolivre.domain.compra.Compra;
import br.com.zupacademy.desafiomercadolivre.domain.pagamento.status.Status;
import br.com.zupacademy.desafiomercadolivre.errors.validators.ExistsValue;
import br.com.zupacademy.desafiomercadolivre.errors.validators.UniqueValue;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProcessaPagamentoRequestDTO {

    @NotNull
    @ExistsValue(message = "não há registro de compra para esse id", domainClass = Compra.class, fieldName = "id")
    private Integer compraId;

    @NotBlank
    @UniqueValue(message = "já existe uma transação processada com esse id", domainClass = Pagamento.class,
            fieldName = "transacaoId")
    private String transacaoId;

    @NotNull
    private Status status;

    public ProcessaPagamentoRequestDTO(@NotNull Integer compraId, @NotBlank String transacaoId, @NotNull Status status) {
        this.compraId = compraId;
        this.transacaoId = transacaoId;
        this.status = status;
    }

    public Pagamento toModel(Compra compra) {
        return new Pagamento(compra, transacaoId, status);
    }

    public Integer getCompraId() {
        return compraId;
    }

    public boolean pagoComSucesso() {
        return status == Status.SUCESSO;
    }
}
