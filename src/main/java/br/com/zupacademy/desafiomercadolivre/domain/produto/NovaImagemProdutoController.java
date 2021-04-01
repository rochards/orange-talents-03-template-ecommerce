package br.com.zupacademy.desafiomercadolivre.domain.produto;

import br.com.zupacademy.desafiomercadolivre.domain.usuario.Usuario;
import br.com.zupacademy.desafiomercadolivre.errors.handlers.APIErrorHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("produtos")
public class NovaImagemProdutoController {

    private final EntityManager em;

    public NovaImagemProdutoController(EntityManager em) {
        this.em = em;
    }

    @Transactional
    @PostMapping("{id}/imagens")
    public ResponseEntity<?> adiciona(@PathVariable Integer id, @Valid ImagemProdutoRequestDTO imagemRequest,
        BindingResult result, @AuthenticationPrincipal Usuario usuario) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new APIErrorHandler(result.getFieldErrors()));
        }

        return ResponseEntity.ok().build();
    }
}
