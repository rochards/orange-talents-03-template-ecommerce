package br.com.zupacademy.desafiomercadolivre.config.autenticacao;

import br.com.zupacademy.desafiomercadolivre.errors.handlers.APIErrorHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
public class LoginController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public LoginController(AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<?> autentica(@RequestBody @Valid LoginRequestDTO loginRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new APIErrorHandler(result.getFieldErrors()));
        }

        var dadosLogin = loginRequest.toUsernamePasswordAuthenticationToken();
        try {
            Authentication authentication = authManager.authenticate(dadosLogin); // chama o ImplementsUserDetailsService
            String token = jwtUtil.geraToken(authentication);

            return ResponseEntity.ok(new LoginResponseDTO("Bearer", token));

        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(String.format("{\"unauthorized\":\"%s\"}",
                    ex.getMessage()));
        }
    }
}
