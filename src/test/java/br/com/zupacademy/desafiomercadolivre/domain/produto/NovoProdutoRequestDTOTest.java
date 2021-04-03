package br.com.zupacademy.desafiomercadolivre.domain.produto;

import br.com.zupacademy.desafiomercadolivre.domain.produto.caracteristica.CaracteristicaProdutoRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NovoProdutoRequestDTOTest {

    @ParameterizedTest
    @MethodSource("geradorDeCaracteristicas")
    @DisplayName("cria produto com diversas características, duplicadas ou nao")
    void testeGetNomesCaracteristicasDuplicadas(int duplicadosEsperados,
        List<CaracteristicaProdutoRequestDTO> caracteristicasDTO) {

        var produtoRequest = new NovoProdutoRequestDTO("nome", new BigDecimal("79.50"), 10, "descricao", 7,
                caracteristicasDTO);

        assertEquals(duplicadosEsperados, produtoRequest.getNomesCaracteristicasDuplicadas().size());
    }

    static Stream<Arguments> geradorDeCaracteristicas() {
        return Stream.of(
                Arguments.of(0, List.of()), // 0, nao entrando no loop
                Arguments.of(0, List.of(new CaracteristicaProdutoRequestDTO("nome1", "descricao"),
                        new CaracteristicaProdutoRequestDTO("nome2", "descricao"))), // 0, entrando no loop com duas caracteristicas nao duplicas
                Arguments.of(1, List.of(new CaracteristicaProdutoRequestDTO("nome1", "descricao"),
                        new CaracteristicaProdutoRequestDTO("nome1", "descricao"))) // 1, entrando no loop com caracteristicas duplicadas
        );
    }
}