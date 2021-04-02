package br.com.zupacademy.desafiomercadolivre.domain.produto;

import br.com.zupacademy.desafiomercadolivre.domain.usuario.Usuario;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

public class NovaOpiniaoRequestDTO {

    @NotNull @Range(min = 1, max = 5)
    private Integer nota;

    @NotBlank
    private String titulo;

    @NotBlank @Length(max = 500)
    private String descricao;

    public NovaOpiniaoRequestDTO(@NotNull @Range(min = 1, max = 5) Integer nota, @NotBlank String titulo,
        @NotBlank @Length(max = 500) String descricao) {

        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Opiniao toModel(Usuario usuario, Produto produto) {
        return new Opiniao(nota, titulo, descricao, usuario, produto);
    }
}
