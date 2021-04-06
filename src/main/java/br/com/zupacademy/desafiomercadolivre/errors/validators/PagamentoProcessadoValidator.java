package br.com.zupacademy.desafiomercadolivre.errors.validators;

import br.com.zupacademy.desafiomercadolivre.domain.pagamento.Pagamento;
import br.com.zupacademy.desafiomercadolivre.domain.pagamento.ProcessaPagamentoRequestDTO;
import br.com.zupacademy.desafiomercadolivre.domain.pagamento.status.Status;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

/** Verifica se o pagamento recebido ja nao foi processado com o status de SUCESSO para uma determinada compra. */
@Component
public class PagamentoProcessadoValidator implements Validator {

    private final EntityManager em;

    public PagamentoProcessadoValidator(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ProcessaPagamentoRequestDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        var pagamentoRequest = (ProcessaPagamentoRequestDTO) target;
        List<Pagamento> pagamentosProcessados = em.createQuery("FROM Pagamento WHERE compra_id=:compra_id", Pagamento.class)
                .setParameter("compra_id", pagamentoRequest.getCompraId()).getResultList();

        boolean pagoComSucesso = pagamentosProcessados.stream()
                .anyMatch(pagamento -> pagamento.getStatus() == Status.SUCESSO);

        if (pagoComSucesso) {
            errors.rejectValue("compraId", "{pagamento.ja.processado.com.sucesso}",
                    "compra tem um pagamento que já foi processado com sucesso pelo sistema");
        }
    }
}
