package br.com.zupacademy.desafiomercadolivre.domain.produto;

import br.com.zupacademy.desafiomercadolivre.domain.categoria.Categoria;
import br.com.zupacademy.desafiomercadolivre.domain.produto.caracteristica.Caracteristica;
import br.com.zupacademy.desafiomercadolivre.domain.produto.caracteristica.NovaCaracteristicaRequestDTO;
import br.com.zupacademy.desafiomercadolivre.domain.produto.imagem.Imagem;
import br.com.zupacademy.desafiomercadolivre.domain.produto.opiniao.Opiniao;
import br.com.zupacademy.desafiomercadolivre.domain.produto.pergunta.Pergunta;
import br.com.zupacademy.desafiomercadolivre.domain.usuario.Usuario;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Produto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2) UNSIGNED")
    private BigDecimal valor;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer quantidade;

    @Column(nullable = false, length = 1000)
    private String descricao;

    @ManyToOne(optional = false)
    private Categoria categoria;

    @ManyToOne(optional = false)
    private Usuario dono;

    /* Eh necessario configurar o cascade para que as operacoes do EntityManager tbm reflitam na tabela da entidade
    CaracteristicaProduto. Sem isso, por exemplo, quando fosse salvar um produto, a lista de caracteristicas nao
    seria salva no banco.
    */
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private Set<Caracteristica> caracteristicas;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
    private List<Imagem> imagens;

    @OneToMany(mappedBy = "produto")
    private List<Opiniao> opinioes;

    @OneToMany(mappedBy = "produto")
    private List<Pergunta> perguntas;

    @Column(nullable = false)
    private OffsetDateTime cadastradoEm;

    @Deprecated
    public Produto() {
    }

    public Produto(String nome, BigDecimal valor, Integer quantidade, String descricao, Categoria categoria,
        Usuario dono, List<NovaCaracteristicaRequestDTO> caracteristicasDTO) {

        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.categoria = categoria;
        this.dono = dono;
        this.cadastradoEm = OffsetDateTime.now();
        setCaracteristicas(caracteristicasDTO);

        Assert.isTrue(caracteristicas.size() >= 3, "o produto não deveria ter menos de 3 características");
    }

    private void setCaracteristicas(List<NovaCaracteristicaRequestDTO> caracteristicasDTO) {
        this.caracteristicas = caracteristicasDTO.stream()
                .map(caracteristica -> caracteristica.toModel(this))
                .collect(Collectors.toSet());
    }

    public void setImagens(List<String> links) {
        this.imagens = links.stream()
                .map(link -> new Imagem(link, this))
                .collect(Collectors.toList());
    }

    public boolean pertenceAUsuario(Usuario possivelDono) {
        return this.dono.equals(possivelDono);
    }

    public boolean abateEstoque(@Positive Integer quantidade) {
        Assert.isTrue(quantidade > 0, "a quantidade deve ser maior que zero");

        if (this.quantidade >= quantidade) {
            this.quantidade -= quantidade;
            return true;
        }

        return false;
    }

    public Usuario getDono() {
        return dono;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public Set<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public List<Imagem> getImagens() {
        return imagens;
    }

    public List<Opiniao> getOpinioes() {
        return opinioes;
    }

    public List<Pergunta> getPerguntas() {
        return perguntas;
    }
}
