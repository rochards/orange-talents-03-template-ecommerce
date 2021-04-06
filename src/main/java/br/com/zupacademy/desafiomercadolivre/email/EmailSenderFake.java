package br.com.zupacademy.desafiomercadolivre.email;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class EmailSenderFake implements EmailSender{

    @Override
    public void envia(String remetente, String destinatario, String titulo, String mensagem) {
        System.out.println("De: " + remetente);
        System.out.println("Para:" + destinatario);
        System.out.println("Título: " + titulo);
        System.out.println("Mensagem: " + mensagem);
    }
}
