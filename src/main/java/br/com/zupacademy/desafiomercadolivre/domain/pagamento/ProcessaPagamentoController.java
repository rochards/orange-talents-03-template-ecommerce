package br.com.zupacademy.desafiomercadolivre.domain.pagamento;

import br.com.zupacademy.desafiomercadolivre.domain.compra.Compra;
import br.com.zupacademy.desafiomercadolivre.domain.pagamento.integracoes.EventosPosCompra;
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
    private final EventosPosCompra eventosPosCompra;

    public ProcessaPagamentoController(EntityManager em, PagamentoProcessadoValidator pagamentoValidator,
        EventosPosCompra eventosPosCompra) {

        this.em = em;
        this.pagamentoValidator = pagamentoValidator;
        this.eventosPosCompra = eventosPosCompra;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(pagamentoValidator);
    }

    /* Nesse controller eu consigo atender tanto as requisicoes do PagSeguro quanto do PayPal. A questao eh que eles
     retornam o status do pagamento de forma diferente: PagSeguro retorna SUCESSO e ERRO; PayPal retorna 1 e 0. Como
     minha classe ProcessaPagamentoRequestDTO define um enum para o parametro em questao, isso fica resolvido. */
    @PostMapping
    @Transactional
    public ResponseEntity<?> processa(@RequestBody @Valid ProcessaPagamentoRequestDTO pagamentoRequest,
        BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new APIErrorHandler(result.getFieldErrors()));
        }

        var compra = em.find(Compra.class, pagamentoRequest.getCompraId());
        if (pagamentoRequest.pagoComSucesso()) {
            compra.finalizaCompra();

            em.merge(compra);

            eventosPosCompra.processaNotaFiscal(compra.getId(), compra.getCompradorId());
            eventosPosCompra.processaRankingVendedores(compra.getId(), compra.getVendedorId());
            eventosPosCompra.enviaEmail("apimercadoLivre@email.com.br", compra.getLoginComprador(), "Compra " +
                    "processada com sucesso", compra.toString());
        } else {
            eventosPosCompra.enviaEmail("apimercadoLivre@email.com.br", compra.getLoginComprador(), "Compra com erro " +
                    "de pagamento", "Tente novamente em " + compra.enviaRegistroCompraParaGatewayPagamento());
        }

        var pagamento = pagamentoRequest.toModel(compra);

        em.persist(pagamento);

        return ResponseEntity.ok().build();
    }
}
