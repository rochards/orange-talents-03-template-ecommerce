package br.com.zupacademy.desafiomercadolivre.domain.produto;

import br.com.zupacademy.desafiomercadolivre.domain.produto.caracteristica.Caracteristica;
import br.com.zupacademy.desafiomercadolivre.domain.produto.caracteristica.DetalheCaracteristicaDTO;
import br.com.zupacademy.desafiomercadolivre.domain.produto.imagem.DetalheImagemDTO;
import br.com.zupacademy.desafiomercadolivre.domain.produto.imagem.Imagem;
import br.com.zupacademy.desafiomercadolivre.domain.produto.opiniao.DetalheOpiniaoDTO;
import br.com.zupacademy.desafiomercadolivre.domain.produto.opiniao.Opiniao;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DetalhesProdutoResponseDTO {

    private String nome;
    private double preco;
    private int quantidade;
    private String descricao;
    private Set<DetalheCaracteristicaDTO> caracteristicas;
    private List<DetalheImagemDTO> imagens;
    private List<DetalheOpiniaoDTO> opinioes;

    public DetalhesProdutoResponseDTO(Produto produto) {
        this.nome = produto.getNome();
        this.preco = produto.getValor().doubleValue();
        this.quantidade = produto.getQuantidade();
        this.descricao = produto.getDescricao();
        setCaracteristicas(produto.getCaracteristicas());
        setImagens(produto.getImagens());
        setOpinioes(produto.getOpinioes());
    }

    private void setCaracteristicas(Set<Caracteristica> caracteristicas) {
        this.caracteristicas = caracteristicas.stream()
                .map(DetalheCaracteristicaDTO::new)
                .collect(Collectors.toSet());
    }

    private void setImagens(List<Imagem> imagens) {
        this.imagens = imagens.stream()
                .map(DetalheImagemDTO::new)
                .collect(Collectors.toList());
    }

    private void setOpinioes(List<Opiniao> opinioes) {
        this.opinioes = opinioes.stream()
                .map(DetalheOpiniaoDTO::new)
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

    public Set<DetalheCaracteristicaDTO> getCaracteristicas() {
        return caracteristicas;
    }

    public List<DetalheImagemDTO> getImagens() {
        return imagens;
    }

    public List<DetalheOpiniaoDTO> getOpinioes() {
        return opinioes;
    }
}
