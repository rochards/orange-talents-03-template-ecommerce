package br.com.zupacademy.desafiomercadolivre.email;

import br.com.zupacademy.desafiomercadolivre.domain.produto.pergunta.Pergunta;
import br.com.zupacademy.desafiomercadolivre.domain.usuario.Usuario;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class EmailSenderFake implements EmailSender{
    @Override
    public void envia(Pergunta pergunta, Usuario donoProduto) {
        System.out.println("Enviando mensagem para o dono do produto com e-mail: " + donoProduto.getLogin());
        System.out.println("Usuário que fez a pergunta: " + pergunta.getUsuario().getLogin());
        System.out.println("Pergunta: " + pergunta.getTitulo());
        System.out.println("Criada em: " + pergunta.getCriadaEm());
        System.out.println("Pergunta sobre o produto: " + pergunta.getProduto().getNome());
    }
}
