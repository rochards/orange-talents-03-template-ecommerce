package br.com.zupacademy.desafiomercadolivre.domain.pagamento.tipos;

public interface GatewayPagamento {

    String enviaRegistroDeCompra(Integer compraId, String urlRetornoAposPagamento);
}
