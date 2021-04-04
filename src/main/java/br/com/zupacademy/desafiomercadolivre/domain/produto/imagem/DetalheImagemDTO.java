package br.com.zupacademy.desafiomercadolivre.domain.produto.imagem;

public class DetalheImagemDTO {

    private String link;

    public DetalheImagemDTO(Imagem imagem) {
        this.link = imagem.getLink();
    }

    public String getLink() {
        return link;
    }
}
