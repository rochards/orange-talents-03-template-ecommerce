package br.com.zupacademy.desafiomercadolivre.errors.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class APIErrorHandler {

    private OffsetDateTime timestamp;
    private int status;
    private List<ErrorField> errors;

    public APIErrorHandler(List<FieldError> fieldErrors) {
        this.timestamp = OffsetDateTime.now();
        this.status = HttpStatus.BAD_REQUEST.value();
        setErrors(fieldErrors);
    }

    private void setErrors(List<FieldError> fieldErrors) {
        this.errors = fieldErrors.stream()
                .map(error -> new ErrorField(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public List<ErrorField> getErrors() {
        return errors;
    }
}
