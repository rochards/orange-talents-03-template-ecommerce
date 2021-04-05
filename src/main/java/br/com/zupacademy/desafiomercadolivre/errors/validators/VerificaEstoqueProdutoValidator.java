package br.com.zupacademy.desafiomercadolivre.errors.validators;

import br.com.zupacademy.desafiomercadolivre.compra.NovaCompraRequestDTO;
import br.com.zupacademy.desafiomercadolivre.domain.produto.Produto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;

public class VerificaEstoqueProdutoValidator implements Validator {

    private final EntityManager em;

    public VerificaEstoqueProdutoValidator(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NovaCompraRequestDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        var compraRequest = (NovaCompraRequestDTO) target;
        var produto = em.find(Produto.class, compraRequest.getProdutoId());

        if (compraRequest.getQuantidade() > produto.getQuantidade()) {
            errors.rejectValue("quantidade", "{quantidade.maior.que.disponivel}", String.format("há apenas '%d' " +
                    "produto(s) no estoque", produto.getQuantidade()));
        }
    }
}
