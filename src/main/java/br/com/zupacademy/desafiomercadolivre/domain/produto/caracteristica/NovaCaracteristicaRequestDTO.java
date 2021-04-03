package br.com.zupacademy.desafiomercadolivre.domain.produto.caracteristica;

import br.com.zupacademy.desafiomercadolivre.domain.produto.Produto;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class NovaCaracteristicaRequestDTO {

    @NotBlank
    private String nome;

    @NotBlank @Length(max = 1000)
    private String descricao;

    public NovaCaracteristicaRequestDTO(@NotBlank String nome, @NotBlank @Length(max = 1000) String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public Caracteristica toModel(Produto produto) {
        return new Caracteristica(nome, descricao, produto);
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
