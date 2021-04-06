package br.com.zupacademy.desafiomercadolivre.domain.produto.pergunta;

import br.com.zupacademy.desafiomercadolivre.domain.produto.Produto;
import br.com.zupacademy.desafiomercadolivre.domain.usuario.Usuario;
import br.com.zupacademy.desafiomercadolivre.email.EmailSender;
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
public class NovaPerguntaController {

    private final EntityManager em;
    private final EmailSender emailSender;

    public NovaPerguntaController(EntityManager em, EmailSender emailSender) {
        this.em = em;
        this.emailSender = emailSender;
    }

    @Transactional
    @PostMapping("{id}/perguntas")
    public ResponseEntity<?> adiciona(@PathVariable Integer id, @RequestBody @Valid NovaPerguntaRequestDTO perguntaRequest,
        BindingResult result, @AuthenticationPrincipal Usuario interessado) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new APIErrorHandler(result.getFieldErrors()));
        }

        var produto = em.find(Produto.class, id);
        if (produto == null) {
            return ResponseEntity.badRequest().body(new APIErrorHandler(List.of(new FieldError("Produto", "id",
                    String.format("produto de id '%d' não encontrado", id))))
            );
        }

        var pergunta = perguntaRequest.toModel(interessado, produto);

        em.persist(pergunta);

        emailSender.envia(produto.getDono().getLogin(), interessado.getLogin(),"Pergunta sobre o produto "
                + produto.getNome(), pergunta.getTitulo());

        return ResponseEntity.ok().build();
    }
}
