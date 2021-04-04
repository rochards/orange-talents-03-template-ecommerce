package br.com.zupacademy.desafiomercadolivre.domain.produto.opiniao;

import java.util.List;

public class DetalheOpiniaoDTO {

    private int nota;
    private String titulo;
    private String descricao;

    public DetalheOpiniaoDTO(Opiniao opiniao) {
        this.nota = opiniao.getNota();
        this.titulo = opiniao.getTitulo();
        this.descricao = opiniao.getDescricao();
    }

    public static double calculaNotaMedia(List<DetalheOpiniaoDTO> opinioes) {
        var optMedia = opinioes.stream()
                .mapToInt(DetalheOpiniaoDTO::getNota)
                .average();

        return optMedia.orElse(0);
    }

    public int getNota() {
        return nota;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }
}
