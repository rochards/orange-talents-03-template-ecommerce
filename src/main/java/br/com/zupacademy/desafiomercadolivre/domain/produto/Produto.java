package br.com.zupacademy.desafiomercadolivre.domain.produto;

import br.com.zupacademy.desafiomercadolivre.domain.categoria.Categoria;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Produto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2) UNSIGNED")
    private BigDecimal valor;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer quantidade;

    @Column(nullable = false, length = 1000)
    private String descricao;

    @ManyToOne(optional = false)
    private Categoria categoria;

    /* Eh necessario configurar o cascade para que as operacoes do EntityManager tbm reflitam na tabela da entidade
    CaracteristicaProduto. Sem isso, por exemplo, quando fosse salvar um produto, a lista de caracteristicas nao
    seria salva no banco.
    */
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private List<CaracteristicaProduto> caracteristicas;

    @Column(nullable = false)
    private OffsetDateTime cadastradoEm;

    @Deprecated
    public Produto() {
    }

    public Produto(String nome, BigDecimal valor, Integer quantidade, String descricao, Categoria categoria,
                   List<CaracteristicaProdutoRequestDTO> caracteristicasDTO) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.categoria = categoria;
        setCaracteristicas(caracteristicasDTO);
        this.cadastradoEm = OffsetDateTime.now();
    }

    private void setCaracteristicas(List<CaracteristicaProdutoRequestDTO> caracteristicasDTO) {
        this.caracteristicas = caracteristicasDTO.stream()
                .map(caracteristica -> new CaracteristicaProduto(caracteristica.getNome(), caracteristica.getDescricao(), this))
                .collect(Collectors.toList());
    }
}
