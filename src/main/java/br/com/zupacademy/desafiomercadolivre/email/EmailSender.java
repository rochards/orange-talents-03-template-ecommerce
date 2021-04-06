package br.com.zupacademy.desafiomercadolivre.email;

public interface EmailSender {

    void envia(String remetente, String destinatario, String titulo, String mensagem);
}
