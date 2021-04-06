package br.com.zupacademy.desafiomercadolivre.domain.pagamento;

import br.com.zupacademy.desafiomercadolivre.domain.compra.Compra;
import br.com.zupacademy.desafiomercadolivre.errors.handlers.APIErrorHandler;
import br.com.zupacademy.desafiomercadolivre.errors.validators.PagamentoProcessadoValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("pagamentos")
public class ProcessaPagamentoController {

    private final EntityManager em;
    private final PagamentoProcessadoValidator pagamentoValidator;

    public ProcessaPagamentoController(EntityManager em, PagamentoProcessadoValidator pagamentoValidator) {
        this.em = em;
        this.pagamentoValidator = pagamentoValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(pagamentoValidator);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> processa(@RequestBody @Valid ProcessaPagamentoRequestDTO pagamentoRequest,
        BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new APIErrorHandler(result.getFieldErrors()));
        }

        if (pagamentoRequest.pagoComSucesso()) {
            var compra = em.find(Compra.class, pagamentoRequest.getCompraId());
            compra.finalizaCompra();

            em.merge(compra);
        }

        var pagamento = pagamentoRequest.toModel(em);

        em.persist(pagamento);

        return ResponseEntity.ok().build();
    }
}
