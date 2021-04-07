package br.com.zupacademy.desafiomercadolivre.domain.pagamento.integracoes;

import br.com.zupacademy.desafiomercadolivre.email.EmailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EventosPosCompra {

    private final EmailSender emailSender;
    private RestTemplate restTemplate = new RestTemplate();

    public EventosPosCompra(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void processaNotaFiscal(Integer compraId, Integer compradorId) {
        String notasFiscaisURL = String.format("http://localhost:8080/notas_fiscais?compraId=%d&compradorId=%d",
                compraId, compradorId);
        String resposta = restTemplate.postForObject(notasFiscaisURL, null, String.class);

        System.out.println(resposta);
    }

    public void processaRankingVendedores(Integer compraId, Integer vendedorId) {
        String url = String.format("http://localhost:8080/ranking_vendedores?compraId=%d&vendedorId=%d", compraId, vendedorId);
        String resposta = restTemplate.postForObject(url, null, String.class);

        System.out.println(resposta);
    }

    public void enviaEmail(String remetente, String destinatario, String titulo, String mensagem) {
        emailSender.envia(remetente, destinatario, titulo, mensagem);
    }
}
