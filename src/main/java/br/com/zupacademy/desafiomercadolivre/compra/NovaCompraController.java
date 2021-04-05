package br.com.zupacademy.desafiomercadolivre.compra;

import br.com.zupacademy.desafiomercadolivre.domain.produto.Produto;
import br.com.zupacademy.desafiomercadolivre.domain.usuario.Usuario;
import br.com.zupacademy.desafiomercadolivre.errors.handlers.APIErrorHandler;
import br.com.zupacademy.desafiomercadolivre.errors.validators.VerificaEstoqueProdutoValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("compras")
public class NovaCompraController {

    private final EntityManager em;

    public NovaCompraController(EntityManager em) {
        this.em = em;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new VerificaEstoqueProdutoValidator(em));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> efetua(@RequestBody @Valid NovaCompraRequestDTO compraRequest, BindingResult result,
        @AuthenticationPrincipal Usuario comprador) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new APIErrorHandler(result.getFieldErrors()));
        }

        var produto = em.find(Produto.class, compraRequest.getProdutoId());
        produto.abateEstoque(compraRequest.getQuantidade());
        var compra = compraRequest.toModel(produto, comprador);

        em.persist(compra);
        em.merge(produto);

        return ResponseEntity.ok().build();
    }
}
