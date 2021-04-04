package br.com.zupacademy.desafiomercadolivre.domain.produto.pergunta;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class DetalhePerguntaDTO {

    private String titulo;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate data;

    public DetalhePerguntaDTO(Pergunta pergunta) {
        this.titulo = pergunta.getTitulo();
        this.data = pergunta.getCriadaEm().toLocalDate();
    }

    public String getTitulo() {
        return titulo;
    }

    public LocalDate getData() {
        return data;
    }
}
