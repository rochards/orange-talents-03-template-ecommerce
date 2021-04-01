package br.com.zupacademy.desafiomercadolivre.domain.produto;

import br.com.zupacademy.desafiomercadolivre.domain.categoria.Categoria;
import br.com.zupacademy.desafiomercadolivre.domain.usuario.Usuario;
import br.com.zupacademy.desafiomercadolivre.errors.validators.ExistsValue;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.*;

public class NovoProdutoRequestDTO {

    @NotBlank
    private String nome;

    @NotNull @Positive
    private BigDecimal valor;

    @Min(0) // nao posso usar @Positive por nao aceitar 0
    private Integer quantidade;

    @NotBlank @Length(max = 1000)
    private String descricao;

    @NotNull
    @ExistsValue(message = "não há categoria cadastrada com o id informado", domainClass = Categoria.class,
            fieldName = "id")
    private Integer categoriaId;

    @NotNull @Size(min = 3) @Valid
    private List<CaracteristicaProdutoRequestDTO> caracteristicas;

    public NovoProdutoRequestDTO(@NotBlank String nome, @Positive BigDecimal valor, @Min(0) Integer quantidade,
        @NotBlank @Length(max = 1000) String descricao, @NotNull Integer categoriaId,
        @NotNull @Size(min = 3) List<CaracteristicaProdutoRequestDTO> caracteristicas) {

        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.categoriaId = categoriaId;
        this.caracteristicas = caracteristicas;
    }

    public Produto toModel(EntityManager em, Usuario dono) {
        var categoria = buscaCategoria(em, categoriaId).get();
        return new Produto(nome, valor, quantidade, descricao, categoria, dono, caracteristicas);
    }

    private Optional<Categoria> buscaCategoria(EntityManager em, Integer id) {
        Assert.notNull(id, "o id não deveria vir nulo");
        return Optional.ofNullable(em.find(Categoria.class, id));
    }

    public List<CaracteristicaProdutoRequestDTO> getCaracteristicas() {
        /* Foi preciso add um setter paras msgs de error. O Jackson ficava reclamando que nao conseguia acessar a
        lista. */
        return caracteristicas;
    }

    public List<String> getNomesCaracteristicasDuplicadas() {
        List<String> nomesDuplicados = new ArrayList<>();
        Set<String> nomesDuplicadosAux = new HashSet<>();

        for (var caracteristica : caracteristicas) {
            String nome = caracteristica.getNome();
            if (!nomesDuplicadosAux.add(nome)) {
                nomesDuplicados.add(nome);
            }
        }

        return nomesDuplicados;
    }
}
