package umc.spring.validation.annotation;

import umc.spring.validation.validator.ChallengingMissionValidator;
import umc.spring.validation.validator.CompleteMissionValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CompleteMissionValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AlreadyCompleteMission {

    String message() default "이미 완수한 미션입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
