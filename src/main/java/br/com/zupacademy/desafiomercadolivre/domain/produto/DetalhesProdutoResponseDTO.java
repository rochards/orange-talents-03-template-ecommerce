package br.com.zupacademy.desafiomercadolivre.domain.produto;

import br.com.zupacademy.desafiomercadolivre.domain.produto.imagem.DetalheImagemDTO;
import br.com.zupacademy.desafiomercadolivre.domain.produto.imagem.Imagem;

import java.util.List;
import java.util.stream.Collectors;

public class DetalhesProdutoResponseDTO {

    private String nome;
    private double preco;
    private int quantidade;
    private String descricao;
    private List<DetalheImagemDTO> imagens;

    public DetalhesProdutoResponseDTO(Produto produto) {
        this.nome = produto.getNome();
        this.preco = produto.getValor().doubleValue();
        this.quantidade = produto.getQuantidade();
        this.descricao = produto.getDescricao();
        setImagens(produto.getImagens());
    }

    private void setImagens(List<Imagem> imagens) {
        this.imagens = imagens.stream()
                .map(DetalheImagemDTO::new)
                .collect(Collectors.toList());
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<DetalheImagemDTO> getImagens() {
        return imagens;
    }
}
