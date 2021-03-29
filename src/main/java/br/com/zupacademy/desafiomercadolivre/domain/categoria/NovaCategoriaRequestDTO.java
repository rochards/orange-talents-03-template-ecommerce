package br.com.zupacademy.desafiomercadolivre.domain.categoria;

import br.com.zupacademy.desafiomercadolivre.errors.validators.UniqueValue;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class NovaCategoriaRequestDTO {

    @NotBlank
    @UniqueValue(message = "esse nome já está cadastrado", domainClass = Categoria.class, fieldName = "nome")
    private String nome;

    @Positive
    private Integer categoriaMaeId;

    // o Jackson nao consegue desserializar parametro de construtor unico, por isso o uso de @JsonProperty
    public NovaCategoriaRequestDTO(@NotBlank @JsonProperty("nome") String nome) {
        this.nome = nome;
    }

    // categoriaMaeId nao entra pelo construtor pq eh parametro nao obrigatorio
    public void setCategoriaMaeId(Integer categoriaMaeId) {
        this.categoriaMaeId = categoriaMaeId;
    }

    public Categoria toModel(EntityManager em) {
        var categoria = new Categoria(nome);
        var categoriaMae = buscaCategoria(em, categoriaMaeId);
        if (categoriaMae != null) {
            categoria.setCategoriaMae(categoriaMae);
        }

        return categoria;
    }

    private Categoria buscaCategoria(EntityManager em, Integer id) {
        return id != null ? em.find(Categoria.class, id) : null;
    }
}
