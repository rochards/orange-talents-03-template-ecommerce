package br.com.zupacademy.desafiomercadolivre.domain.pagamento.tipos;

public class PayPal implements GatewayPagamento{

    @Override
    public String enviaRegistroDeCompra(Integer compraId, String urlRetornoAposPagamento) {
        return String.format("paypal.com?buyerId=%d&redirectUrl=%s", compraId, urlRetornoAposPagamento);
    }
}
