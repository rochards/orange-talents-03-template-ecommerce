package br.com.zupacademy.desafiomercadolivre.domain.pagamento.tipos;

public class PagSeguro implements GatewayPagamento {
    
    @Override
    public String enviaRegistroDeCompra(Integer compraId, String urlRetornoAposPagamento) {
        return String.format("pagseguro.com?buyerId=%d&redirectUrl=%s", compraId, urlRetornoAposPagamento);
    }
}
