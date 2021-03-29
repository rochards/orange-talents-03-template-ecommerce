package br.com.zupacademy.desafiomercadolivre.domain.categoria;

import br.com.zupacademy.desafiomercadolivre.errors.validators.UniqueValue;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class NovaCategoriaRequestDTO {

    @NotBlank
    @UniqueValue(message = "esse nome já está cadastrado", domainClass = Categoria.class, fieldName = "nome")
    private String nome;

    public NovaCategoriaRequestDTO(@NotBlank @JsonProperty("nome") String nome) {
        this.nome = nome;
    }

    public Categoria toModel() {
        return new Categoria(nome);
    }
}
