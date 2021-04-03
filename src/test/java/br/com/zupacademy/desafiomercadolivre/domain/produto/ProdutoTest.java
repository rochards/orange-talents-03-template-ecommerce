package br.com.zupacademy.desafiomercadolivre.domain.produto;

import br.com.zupacademy.desafiomercadolivre.domain.categoria.Categoria;
import br.com.zupacademy.desafiomercadolivre.domain.produto.caracteristica.NovaCaracteristicaRequestDTO;
import br.com.zupacademy.desafiomercadolivre.domain.usuario.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

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

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Produto("nome", new BigDecimal("79.50"),
                10, "descricao", categoria, dono, caracteristicasDTO));
    }
}