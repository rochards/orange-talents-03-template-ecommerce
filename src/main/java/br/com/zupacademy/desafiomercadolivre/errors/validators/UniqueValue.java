package br.com.zupacademy.desafiomercadolivre.errors.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Documented
@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueValueValidator.class)
public @interface UniqueValue {

    String message() default "custom.validation.UniqueValue.message";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    Class<?> domainClass();

    String fieldName();

    @Documented
    @Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
    @Retention(RUNTIME)
    @interface List {
        UniqueValue[] value();
    }
}
