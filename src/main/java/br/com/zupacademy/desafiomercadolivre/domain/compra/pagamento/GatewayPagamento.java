package br.com.zupacademy.desafiomercadolivre.domain.compra.pagamento;

public interface GatewayPagamento {

    String enviaRegistroDeCompra(Integer compraId, String urlRetornoAposPagamento);
}
