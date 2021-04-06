package br.com.zupacademy.desafiomercadolivre.domain.pagamento.status;

public enum Status {

    ERRO(0), SUCESSO(1);

    private int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
