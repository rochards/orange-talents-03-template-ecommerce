package br.com.zupacademy.desafiomercadolivre.domain.produto;

public class DetalhesProdutoResponseDTO {

    private String nome;
    private double preco;
    private String descricao;

    public DetalhesProdutoResponseDTO(Produto produto) {
        this.nome = produto.getNome();
        this.preco = produto.getValor().doubleValue();
        this.descricao = produto.getDescricao();
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public String getDescricao() {
        return descricao;
    }
}
