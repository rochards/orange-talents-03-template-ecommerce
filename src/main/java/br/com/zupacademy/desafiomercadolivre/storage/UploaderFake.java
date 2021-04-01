package br.com.zupacademy.desafiomercadolivre.storage;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UploaderFake {

    /**
     * @param imagens lista de MultipartFile
     * @return links das imagens salvas
     * */
    public List<String> envia(List<MultipartFile> imagens) {
        return imagens.stream()
                .map(imagem -> "http://storage.aws/" + UUID.randomUUID() + "-" +imagem.getOriginalFilename())
                .collect(Collectors.toList());
    }
}
