package br.com.zupacademy.desafiomercadolivre.errors.validators;

import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {

    private String fieldName;
    private Class<?> domainClass;

    private final EntityManager entityManager;

    public UniqueValueValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void initialize(UniqueValue constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
        this.domainClass = constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        Query query = entityManager.createQuery(String.format("SELECT 1 FROM %s WHERE %s=:field", domainClass.getName(),
                fieldName)).setParameter("field", value);
        List<?> resultList = query.getResultList();

        Assert.state(resultList.size() <= 1, String.format("foi encontrado mais de um %s com o atributo %s",
                domainClass.getName(), fieldName));

        return resultList.isEmpty();
    }
}
