package br.com.zupacademy.desafiomercadolivre.errors.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static br.com.zupacademy.desafiomercadolivre.errors.validators.ExistsValue.List;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = ExistsValueValidator.class)
@Repeatable(List.class)
public @interface ExistsValue {

    String message() default "{custom.validation.ExistsValue.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    Class<?> domainClass();

    String fieldName();

    @Documented
    @Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
    @Retention(RUNTIME)
    @interface List {
        ExistsValue[] value();
    }
}
