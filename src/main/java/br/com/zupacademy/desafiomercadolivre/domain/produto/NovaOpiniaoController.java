package br.com.zupacademy.desafiomercadolivre.domain.produto;

import br.com.zupacademy.desafiomercadolivre.domain.usuario.Usuario;
import br.com.zupacademy.desafiomercadolivre.errors.handlers.APIErrorHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("produtos")
public class NovaOpiniaoController {

    private final EntityManager em;

    public NovaOpiniaoController(EntityManager em) {
        this.em = em;
    }

    @Transactional
    @PostMapping("{id}/opinioes")
    public ResponseEntity<?> adiciona(@PathVariable Integer id, @RequestBody @Valid NovaOpiniaoRequestDTO opiniaoRequest,
        BindingResult result, @AuthenticationPrincipal Usuario consumidor) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new APIErrorHandler(result.getFieldErrors()));
        }

        var produto = em.find(Produto.class, id);
        if (produto == null) {
            return ResponseEntity.badRequest().body(new APIErrorHandler(List.of(new FieldError("Produto", "id",
                    String.format("produto de id '%d' não encontrado", id))))
            );
        }

        var opiniao = opiniaoRequest.toModel(consumidor, produto);

        em.persist(opiniao);

        return ResponseEntity.ok().build();
    }
}
