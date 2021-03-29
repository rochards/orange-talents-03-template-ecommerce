package br.com.zupacademy.desafiomercadolivre.errors.validators;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ExistsValueValidator implements ConstraintValidator<ExistsValue, Object> {

    private String fieldName;
    private Class<?> domainClass;

    private final EntityManager entityManager;

    public ExistsValueValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void initialize(ExistsValue constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
        this.domainClass = constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            // The Jakarta Bean Validation specification recommends to consider null values as being valid
            return true;
        }

        Query query = entityManager.createQuery(String.format("SELECT 1 FROM %s WHERE %s=:field", domainClass.getName(),
                fieldName)).setParameter("field", value);
        List<?> resultList = query.getResultList();

        return !resultList.isEmpty();
    }
}
