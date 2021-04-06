package br.com.zupacademy.desafiomercadolivre.integracao;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Rest controller para simular a integracao com um sistema de processamento de notas fiscais */
@RestController
@RequestMapping("notas_fiscais")
public class ProcessaNotaFiscalController {

    @PostMapping
    public String processaNotas(@RequestParam("compraId") Integer compraId,
        @RequestParam("compradorId") Integer compradorId) {

        return String.format("Nota fiscal gerada para: compra %d do comprador %d", compraId, compradorId);
    }
}
