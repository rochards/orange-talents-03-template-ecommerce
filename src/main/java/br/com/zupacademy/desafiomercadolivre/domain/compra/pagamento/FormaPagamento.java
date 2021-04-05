package br.com.zupacademy.desafiomercadolivre.domain.compra.pagamento;

public enum FormaPagamento {

    PAYPAL(new PayPal()),
    PAGSEGURO(new PagSeguro());

    private GatewayPagamento gatewayPagamento;

    FormaPagamento(GatewayPagamento gatewayPagamento) {
        this.gatewayPagamento = gatewayPagamento;
    }

    public GatewayPagamento getGatewayPagamento() {
        return gatewayPagamento;
    }
}
