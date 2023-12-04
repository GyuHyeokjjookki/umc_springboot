package umc.spring.validation.annotation;

import umc.spring.validation.validator.PagesNegativeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PagesNegativeValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NegativePages {

    String message() default "페이지 값이 음수입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
