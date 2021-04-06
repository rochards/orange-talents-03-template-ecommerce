package br.com.zupacademy.desafiomercadolivre.domain.pagamento;

import br.com.zupacademy.desafiomercadolivre.domain.compra.Compra;
import br.com.zupacademy.desafiomercadolivre.errors.handlers.APIErrorHandler;
import br.com.zupacademy.desafiomercadolivre.errors.validators.PagamentoProcessadoValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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


            var restTemplate = new RestTemplate();

            String notasFiscaisURL = String.format("http://localhost:8080/notas_fiscais?compraId=%d" +
                    "&compradorId=%d", compra.getId(), compra.getCompradorId());
            String retornoNotasFiscais = restTemplate.postForObject(notasFiscaisURL, null, String.class);
            System.out.println(retornoNotasFiscais);

            String rankingVendedoresURL = String.format("http://localhost:8080/ranking_vendedores?compraId=%d" +
                    "&vendedorId=%d", compra.getId(), compra.getVendedorId());
            String retornorankingVendedores = restTemplate.postForObject(rankingVendedoresURL, null, String.class);
            System.out.println(retornorankingVendedores);
        }

        var pagamento = pagamentoRequest.toModel(em);

        em.persist(pagamento);

        return ResponseEntity.ok().build();
    }
}
