package br.com.zupacademy.desafiomercadolivre.domain.produto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;

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
            return ResponseEntity.notFound().build();
        }

        var detalhesResponse = new DetalhesProdutoResponseDTO(produto);
        return ResponseEntity.ok(detalhesResponse);
    }
}
