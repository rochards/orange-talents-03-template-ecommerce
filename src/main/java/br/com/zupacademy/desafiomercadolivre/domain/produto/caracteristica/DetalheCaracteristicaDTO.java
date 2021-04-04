package br.com.zupacademy.desafiomercadolivre.domain.produto.caracteristica;

public class DetalheCaracteristicaDTO {

    private String nome;
    private String descricao;

    public DetalheCaracteristicaDTO(Caracteristica caracteristica) {
        this.nome = caracteristica.getNome();
        this.descricao = caracteristica.getDescricao();
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
