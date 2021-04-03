package br.com.zupacademy.desafiomercadolivre.domain.produto;

import br.com.zupacademy.desafiomercadolivre.errors.handlers.APIErrorHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.List;

@RestController
@RequestMapping("produtos")
public class DetalhesProdutoController {

    private final EntityManager em;

    public DetalhesProdutoController(EntityManager em) {
        this.em = em;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> detalhes(@PathVariable Integer id) {

        var produto = em.find(Produto.class, id);
        if (produto == null) {
            return ResponseEntity.badRequest().body(new APIErrorHandler(List.of(new FieldError("Produto", "id",
                    String.format("produto de id '%d' não encontrado", id))))
            );
        }

        var detalhesResponse = new DetalhesProdutoResponseDTO(produto);
        return ResponseEntity.ok(detalhesResponse);
    }
}
