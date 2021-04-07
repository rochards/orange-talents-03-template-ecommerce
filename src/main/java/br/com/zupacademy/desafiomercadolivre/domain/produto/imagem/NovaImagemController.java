package br.com.zupacademy.desafiomercadolivre.domain.produto.imagem;

import br.com.zupacademy.desafiomercadolivre.domain.produto.Produto;
import br.com.zupacademy.desafiomercadolivre.domain.usuario.Usuario;
import br.com.zupacademy.desafiomercadolivre.errors.handlers.APIErrorHandler;
import br.com.zupacademy.desafiomercadolivre.storage.Uploader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("produtos")
public class NovaImagemController {

    private final EntityManager em;
    private final Uploader uploaderFake;

    public NovaImagemController(EntityManager em, Uploader uploaderFake) {
        this.em = em;
        this.uploaderFake = uploaderFake;
    }

    @Transactional
    @PostMapping("{id}/imagens")
    public ResponseEntity<?> adiciona(@PathVariable Integer id, @Valid NovaImagemRequestDTO imagemRequest,
        BindingResult result, @AuthenticationPrincipal Usuario usuario) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new APIErrorHandler(result.getFieldErrors()));
        }

        /* Fluxo caso fosse em producao
        * 1 - enviar imagens para um local de armazenamento. Ex.: AWS S3
        * 2 - recupear os links gerados acima das imagens;
        * 3 - associar os links com seus devidos produtos.
        * */
        var produto = em.find(Produto.class, id);
        if (produto == null) {
            return ResponseEntity.badRequest().body(new APIErrorHandler(List.of(new FieldError("Produto", "id",
                    String.format("produto de id '%d' não encontrado", id))))
            );
        }
        if (!produto.pertenceAUsuario(usuario)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new APIErrorHandler(List.of(new FieldError(
                    "Produto", "id", String.format("usuário não é dono de 'produto/%d'", id))), HttpStatus.FORBIDDEN)
            );
        }

        List<String> links = uploaderFake.envia(imagemRequest.getImagens());
        produto.setImagens(links);

        em.merge(produto); // faz o update do produto e add o link da imagem no banco por cascade

        return ResponseEntity.ok(links);
    }
}
