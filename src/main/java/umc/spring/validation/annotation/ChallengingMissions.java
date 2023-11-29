package umc.spring.validation.annotation;

import umc.spring.validation.validator.ChallengingMissionValidator;
import umc.spring.validation.validator.MissionExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ChallengingMissionValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ChallengingMissions {

    String message() default "해당 미션은 이미 도전중 입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
