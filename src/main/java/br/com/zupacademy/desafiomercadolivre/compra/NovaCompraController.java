package br.com.zupacademy.desafiomercadolivre.compra;

import br.com.zupacademy.desafiomercadolivre.domain.produto.Produto;
import br.com.zupacademy.desafiomercadolivre.domain.usuario.Usuario;
import br.com.zupacademy.desafiomercadolivre.errors.handlers.APIErrorHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    @Transactional
    public ResponseEntity<?> efetua(@RequestBody @Valid NovaCompraRequestDTO compraRequest, BindingResult result,
        @AuthenticationPrincipal Usuario comprador) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new APIErrorHandler(result.getFieldErrors()));
        }

        var produto = em.find(Produto.class, compraRequest.getProdutoId());
        var compra = compraRequest.toModel(produto, comprador);

        em.persist(compra);

        return ResponseEntity.ok().build();
    }
}
