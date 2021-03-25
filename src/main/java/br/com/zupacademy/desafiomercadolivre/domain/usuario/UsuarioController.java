package br.com.zupacademy.desafiomercadolivre.domain.usuario;

import br.com.zupacademy.desafiomercadolivre.errors.APIErrorHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<?> cadastra(@RequestBody @Valid UsuarioRequestDTO usuarioRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new APIErrorHandler(result.getFieldErrors()));
        }

        Usuario usuario = usuarioRequest.toModel();

        usuario = usuarioRepository.save(usuario);

        return ResponseEntity.ok(new UsuarioResponseDTO(usuario));
    }
}
