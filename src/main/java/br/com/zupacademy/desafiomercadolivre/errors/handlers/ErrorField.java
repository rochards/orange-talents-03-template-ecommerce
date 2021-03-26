package br.com.zupacademy.desafiomercadolivre.errors.handlers;

public class ErrorField {

    private String field;
    private String message;

    public ErrorField(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
