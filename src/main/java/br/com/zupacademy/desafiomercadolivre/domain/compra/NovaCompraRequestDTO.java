package br.com.zupacademy.desafiomercadolivre.domain.compra;

import br.com.zupacademy.desafiomercadolivre.domain.produto.Produto;
import br.com.zupacademy.desafiomercadolivre.domain.usuario.Usuario;
import br.com.zupacademy.desafiomercadolivre.errors.validators.ExistsValue;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class NovaCompraRequestDTO {

    @NotNull
    @ExistsValue(message = "não há produto cadastrado com o id informado", domainClass = Produto.class, fieldName = "id")
    private Integer produtoId;

    @NotNull @Positive
    private Integer quantidade;

    @NotBlank
    private String formaPagamento;

    public NovaCompraRequestDTO(Integer produtoId, Integer quantidade, String formaPagamento) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.formaPagamento = formaPagamento;
    }

    public Integer getProdutoId() {
        return produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Compra toModel(Produto produto, Usuario comprador) {
        return new Compra(produto.getValor(), quantidade,formaPagamento, produto, comprador);
    }
}
