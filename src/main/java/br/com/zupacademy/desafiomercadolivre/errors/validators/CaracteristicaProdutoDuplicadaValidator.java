package br.com.zupacademy.desafiomercadolivre.errors.validators;

import br.com.zupacademy.desafiomercadolivre.domain.produto.NovoProdutoRequestDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * O atributo que define a duplicidade eh o nome da caracteristica
 * */
public class CaracteristicaProdutoDuplicadaValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return NovoProdutoRequestDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        var produtoRequest = (NovoProdutoRequestDTO) target;
        List<String> nomesDuplicados = produtoRequest.getNomesCaracteristicasDuplicadas();
        if (!nomesDuplicados.isEmpty()) {
            errors.rejectValue("caracteristicas", "{caracteristicas.duplicadas}",
                    "características duplicadas: " + nomesDuplicados);
        }
    }
}
