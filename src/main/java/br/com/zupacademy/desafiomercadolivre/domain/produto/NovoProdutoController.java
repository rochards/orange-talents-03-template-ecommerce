package br.com.zupacademy.desafiomercadolivre.domain.produto;

import br.com.zupacademy.desafiomercadolivre.domain.usuario.Usuario;
import br.com.zupacademy.desafiomercadolivre.errors.handlers.APIErrorHandler;
import br.com.zupacademy.desafiomercadolivre.errors.validators.CaracteristicaProdutoDuplicadaValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("produtos")
public class NovoProdutoController {

    private final EntityManager em;

    public NovoProdutoController(EntityManager em) {
        this.em = em;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new CaracteristicaProdutoDuplicadaValidator());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastra(@RequestBody @Valid NovoProdutoRequestDTO produtoRequest, BindingResult result,
        @AuthenticationPrincipal Usuario usuario) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new APIErrorHandler(result.getFieldErrors()));
        }

        var produto = produtoRequest.toModel(em, usuario);

        em.persist(produto);

        return ResponseEntity.ok().build();
    }
}
