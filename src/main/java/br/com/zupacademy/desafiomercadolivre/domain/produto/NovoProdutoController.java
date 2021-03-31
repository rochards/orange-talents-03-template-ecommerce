package br.com.zupacademy.desafiomercadolivre.domain.produto;

import br.com.zupacademy.desafiomercadolivre.errors.handlers.APIErrorHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("produtos")
public class NovoProdutoController {

    @PostMapping
    public ResponseEntity<?> cadastra(@RequestBody @Valid NovoProdutoRequestDTO produtoRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new APIErrorHandler(result.getFieldErrors()));
        }

        return ResponseEntity.ok().build();
    }
}
