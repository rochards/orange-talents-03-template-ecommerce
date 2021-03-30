package br.com.zupacademy.desafiomercadolivre.config.autenticacao;

public class LoginResponseDTO {

    private final String tipo;
    private final String token;

    public LoginResponseDTO(String tipo, String token) {
        this.tipo = tipo;
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public String getToken() {
        return token;
    }
}
