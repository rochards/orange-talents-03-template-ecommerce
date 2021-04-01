package br.com.zupacademy.desafiomercadolivre.domain.produto;

import br.com.zupacademy.desafiomercadolivre.domain.usuario.Usuario;
import br.com.zupacademy.desafiomercadolivre.errors.handlers.APIErrorHandler;
import br.com.zupacademy.desafiomercadolivre.storage.UploaderFake;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("produtos")
public class NovaImagemProdutoController {

    private final EntityManager em;
    private final UploaderFake uploaderFake;

    public NovaImagemProdutoController(EntityManager em, UploaderFake uploaderFake) {
        this.em = em;
        this.uploaderFake = uploaderFake;
    }

    @Transactional
    @PostMapping("{id}/imagens")
    public ResponseEntity<?> adiciona(@PathVariable Integer id, @Valid ImagemProdutoRequestDTO imagemRequest,
        BindingResult result, @AuthenticationPrincipal Usuario usuario) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new APIErrorHandler(result.getFieldErrors()));
        }

        /* Fluxo caso fosse em producao
        * 1 - enviar imagens para um local de armazenamento. Ex.: AWS S3
        * 2 - recupear os links gerados acima das imagens;
        * 3 - associar os links com seus devidos produtos.
        * */

        List<String> links = uploaderFake.envia(imagemRequest.getImagens());
        System.out.println(links);

        return ResponseEntity.ok().build();
    }
}
