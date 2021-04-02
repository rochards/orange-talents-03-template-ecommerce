package br.com.zupacademy.desafiomercadolivre.email;

import br.com.zupacademy.desafiomercadolivre.domain.produto.pergunta.Pergunta;
import br.com.zupacademy.desafiomercadolivre.domain.usuario.Usuario;

public interface EmailSender {

    void envia(Pergunta pergunta, Usuario donoProduto);
}
