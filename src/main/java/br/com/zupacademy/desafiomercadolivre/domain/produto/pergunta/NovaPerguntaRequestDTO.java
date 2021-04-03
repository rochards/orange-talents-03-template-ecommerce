package br.com.zupacademy.desafiomercadolivre.domain.produto.pergunta;

import br.com.zupacademy.desafiomercadolivre.domain.produto.Produto;
import br.com.zupacademy.desafiomercadolivre.domain.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class NovaPerguntaRequestDTO {

    @NotBlank
    private String titulo;

    public NovaPerguntaRequestDTO(@NotBlank @JsonProperty("titulo") String titulo) {
        this.titulo = titulo;
    }

    public Pergunta toModel(Usuario interessado, Produto produto) {
        return new Pergunta(titulo, interessado, produto);
    }
}
