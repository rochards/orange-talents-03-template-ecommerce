package br.com.zupacademy.desafiomercadolivre.domain.produto;

import br.com.zupacademy.desafiomercadolivre.domain.produto.caracteristica.DetalheCaracteristicaDTO;
import br.com.zupacademy.desafiomercadolivre.domain.produto.imagem.DetalheImagemDTO;
import br.com.zupacademy.desafiomercadolivre.domain.produto.opiniao.DetalheOpiniaoDTO;
import br.com.zupacademy.desafiomercadolivre.domain.produto.pergunta.DetalhePerguntaDTO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DetalhesProdutoResponseDTO {

    private String nome;
    private double preco;
    private int quantidade;
    private String descricao;
    private double notaMedia;
    private int totalDeNotas;
    private Set<DetalheCaracteristicaDTO> caracteristicas;
    private List<DetalheImagemDTO> imagens;
    private List<DetalheOpiniaoDTO> opinioes;
    private List<DetalhePerguntaDTO> perguntas;

    public DetalhesProdutoResponseDTO(Produto produto) {
        this.nome = produto.getNome();
        this.preco = produto.getValor().doubleValue();
        this.quantidade = produto.getQuantidade();
        this.descricao = produto.getDescricao();
        setCaracteristicas(produto);
        setImagens(produto);
        setOpinioes(produto);
        this.notaMedia = DetalheOpiniaoDTO.calculaNotaMedia(opinioes);
        this.totalDeNotas = opinioes.size();
        setPerguntas(produto);
    }

    private void setCaracteristicas(Produto produto) {
        this.caracteristicas = produto.getCaracteristicas().stream()
                .map(DetalheCaracteristicaDTO::new)
                .collect(Collectors.toSet());
    }

    private void setImagens(Produto produto) {
        this.imagens = produto.getImagens().stream()
                .map(DetalheImagemDTO::new)
                .collect(Collectors.toList());
    }

    private void setOpinioes(Produto produto) {
        this.opinioes = produto.getOpinioes().stream()
                .map(DetalheOpiniaoDTO::new)
                .collect(Collectors.toList());
    }

    private void setPerguntas(Produto produto) {
        this.perguntas = produto.getPerguntas().stream()
                .map(DetalhePerguntaDTO::new)
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

    public double getNotaMedia() {
        return notaMedia;
    }

    public int getTotalDeNotas() {
        return totalDeNotas;
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

    public List<DetalhePerguntaDTO> getPerguntas() {
        return perguntas;
    }
}
