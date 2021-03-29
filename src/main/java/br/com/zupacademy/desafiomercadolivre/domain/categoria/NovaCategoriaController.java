package br.com.zupacademy.desafiomercadolivre.domain.categoria;

import br.com.zupacademy.desafiomercadolivre.errors.handlers.APIErrorHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("categorias")
public class NovaCategoriaController {

    private final EntityManager em;

    public NovaCategoriaController(EntityManager em) {
        this.em = em;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastra(@RequestBody @Valid NovaCategoriaRequestDTO categoriaRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new APIErrorHandler(result.getFieldErrors()));
        }

        var categoria = categoriaRequest.toModel(em);

        em.persist(categoria);

        return ResponseEntity.ok().build();
    }
}
