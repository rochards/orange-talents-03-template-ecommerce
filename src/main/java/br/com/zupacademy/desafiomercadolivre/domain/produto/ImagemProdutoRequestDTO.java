package br.com.zupacademy.desafiomercadolivre.domain.produto;

import br.com.zupacademy.desafiomercadolivre.domain.usuario.Usuario;
import br.com.zupacademy.desafiomercadolivre.errors.validators.ExistsValue;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public class ImagemProdutoRequestDTO {

    @NotNull
    @ExistsValue(message = "não há produto cadastrado com o id informado", domainClass = Produto.class, fieldName = "id")
    private Integer produtoId;

    @NotBlank
    private String link;

    public ImagemProdutoRequestDTO(@NotNull Integer produtoId, @NotBlank String link) {
        this.produtoId = produtoId;
        this.link = link;
    }

    public Optional<ImagemProduto> toModel(EntityManager em, Usuario usuario) {
        var produto = buscaProduto(em, produtoId).get();

        if (produtoPertenceAUsuario(produto, usuario)) {
            return Optional.of(new ImagemProduto(link, produto));
        }

        return Optional.empty();
    }

    private Optional<Produto> buscaProduto(EntityManager em, Integer id) {
        Assert.notNull(id, "o id não deveria vir nulo");
        return Optional.ofNullable(em.find(Produto.class, id));
    }

    private boolean produtoPertenceAUsuario(Produto produto, Usuario usuario) {
        var usuarioId = usuario.getId();
        var donoProdutoId = produto.getDono().getId();

        return usuarioId.equals(donoProdutoId);
    }
}
