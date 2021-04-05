package br.com.zupacademy.desafiomercadolivre.domain.compra;

import br.com.zupacademy.desafiomercadolivre.domain.produto.Produto;
import br.com.zupacademy.desafiomercadolivre.domain.usuario.Usuario;
import br.com.zupacademy.desafiomercadolivre.errors.handlers.APIErrorHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestController
@RequestMapping("compras")
public class NovaCompraController {

    private final EntityManager em;

    public NovaCompraController(EntityManager em) {
        this.em = em;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> efetua(@RequestBody @Valid NovaCompraRequestDTO compraRequest, BindingResult result,
        @AuthenticationPrincipal Usuario comprador, HttpServletResponse response) throws IOException {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new APIErrorHandler(result.getFieldErrors()));
        }

        var produto = em.find(Produto.class, compraRequest.getProdutoId());
        boolean abateuEstoque = produto.abateEstoque(compraRequest.getQuantidade());

        /* Estou fazendo a verificacao do estoque aqui para garantir a atomicidade das operacoes de verificar se tem
        estoque e abater. Antes isso estava sendo feito de forma separada, o que podia acarretar em problemas de
        inconsistencia de dados, pois podia acontecer de entre a consulta e a atualizacao do estoque um outro pedido
        de compra ser processado. Como estou dentro de uma transacao (@Transactional), consigo mitigar esse problema. */
        if (abateuEstoque) {
            var compra = compraRequest.toModel(produto, comprador);

            em.persist(compra);
            em.merge(produto); // em especial, no caso do Hibernate, ele ja conseguiria fazer o merge sem eu explicitar

            String urlToRedirect = compra.enviaRegistroCompraParaGatewayPagamento();
            System.out.println("Registro no gateway de pagamento: " + urlToRedirect);
            System.out.println("Enviando e-mail para: " + produto.getDono().getLogin());

//          response.sendRedirect(urlToRedirect); deixei comentado para nao ficar retorando 404
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(
                new APIErrorHandler(List.of(new FieldError("Produto","quantidade", "não há produtos suficientes no " +
                        "estoque")), UNPROCESSABLE_ENTITY)
        );
    }
}
