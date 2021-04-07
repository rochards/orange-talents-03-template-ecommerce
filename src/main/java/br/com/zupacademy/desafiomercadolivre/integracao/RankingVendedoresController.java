package br.com.zupacademy.desafiomercadolivre.integracao;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Rest controller para simular a integracao com um sistema de ranking de vendedores */
@RestController
@RequestMapping("ranking_vendedores")
public class RankingVendedoresController {

    @PostMapping
    public String atualizaRanking(@RequestParam("compraId") Integer compraId,
        @RequestParam("vendedorId") Integer vendedorId) {

        return String.format("Nova venda gerada para: vendedor %d da compra %d", vendedorId, compraId);
    }
}
