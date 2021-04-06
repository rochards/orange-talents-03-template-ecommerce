package br.com.zupacademy.desafiomercadolivre.domain.produto;

import br.com.zupacademy.desafiomercadolivre.domain.categoria.Categoria;
import br.com.zupacademy.desafiomercadolivre.domain.produto.caracteristica.NovaCaracteristicaRequestDTO;
import br.com.zupacademy.desafiomercadolivre.domain.usuario.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProdutoTest {

    @Test
    @DisplayName("um produto precisa de no mínimo 3 características")
    void teste1() {

        List<NovaCaracteristicaRequestDTO> caracteristicasDTO = List.of(
                new NovaCaracteristicaRequestDTO("nome1", "descricao"),
                new NovaCaracteristicaRequestDTO("nome2", "descricao"),
                new NovaCaracteristicaRequestDTO("nome3", "descricao"));

        var categoria = new Categoria("categoria");
        var dono = new Usuario("teste@email.com.br", "123456");

        new Produto("nome", new BigDecimal("79.50"), 10, "descricao", categoria, dono, caracteristicasDTO);
    }

    @Test
    @DisplayName("um produto não deveria ser criado com menos de 3 características")
    void teste2() {

        List<NovaCaracteristicaRequestDTO> caracteristicasDTO = List.of(
                new NovaCaracteristicaRequestDTO("nome1", "descricao"),
                new NovaCaracteristicaRequestDTO("nome2", "descricao"));

        var categoria = new Categoria("categoria");
        var dono = new Usuario("teste@email.com.br", "123456");

        assertThrows(IllegalArgumentException.class, () -> new Produto("nome", new BigDecimal("79.50"),
                10, "descricao", categoria, dono, caracteristicasDTO));
    }

    @ParameterizedTest
    @CsvSource({"0", "-100"})
    @DisplayName("deveria lançar exceção caso quantidade fornecida <= 0")
    void teste3(int quantidade) {

        List<NovaCaracteristicaRequestDTO> caracteristicasDTO = List.of(
                new NovaCaracteristicaRequestDTO("nome1", "descricao"),
                new NovaCaracteristicaRequestDTO("nome2", "descricao"),
                new NovaCaracteristicaRequestDTO("nome3", "descricao"));

        var categoria = new Categoria("categoria");
        var dono = new Usuario("teste@email.com.br", "123456");
        var produto = new Produto("nome", new BigDecimal("79.50"), 10, "descricao", categoria, dono, caracteristicasDTO);

        assertThrows(IllegalArgumentException.class, () -> produto.abateEstoque(quantidade));
    }

    @ParameterizedTest
    @CsvSource({"1, 1, true", "1, 2, false", "4, 2, true"})
    @DisplayName("não deveria abater estoque fosse resultar em estoque < 0")
    void teste4(int estoque, int quantidade, boolean resultadoEsperado) {

        List<NovaCaracteristicaRequestDTO> caracteristicasDTO = List.of(
                new NovaCaracteristicaRequestDTO("nome1", "descricao"),
                new NovaCaracteristicaRequestDTO("nome2", "descricao"),
                new NovaCaracteristicaRequestDTO("nome3", "descricao"));

        var categoria = new Categoria("categoria");
        var dono = new Usuario("teste@email.com.br", "123456");
        var produto = new Produto("nome", new BigDecimal("79.50"), estoque, "descricao", categoria, dono,
                caracteristicasDTO);

        boolean estoqueAbatido = produto.abateEstoque(quantidade);
        assertEquals(resultadoEsperado, estoqueAbatido);
    }
}