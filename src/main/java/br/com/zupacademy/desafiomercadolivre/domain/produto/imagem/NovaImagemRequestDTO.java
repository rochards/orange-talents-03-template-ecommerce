package br.com.zupacademy.desafiomercadolivre.domain.produto.imagem;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class NovaImagemRequestDTO {

    @NotNull @Size(min = 1)
    private List<MultipartFile> imagens;

    public NovaImagemRequestDTO(@NotNull @Size(min = 1) List<MultipartFile> imagens) {
        this.imagens = imagens;
    }

    public List<MultipartFile> getImagens() {
        return imagens;
    }
}
