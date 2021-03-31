package br.com.zupacademy.desafiomercadolivre.domain.produto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class CaracteristicaProdutoRequestDTO {

    @NotBlank
    private String nome;

    @NotBlank @Length(max = 1000)
    private String descricao;

    public CaracteristicaProdutoRequestDTO(@NotBlank String nome, @NotBlank @Length(max = 1000) String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }
}
