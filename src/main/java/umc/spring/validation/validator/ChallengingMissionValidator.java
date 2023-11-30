package umc.spring.validation.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.domain.enums.MissionStatus;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.repository.MemberMissionRepository;
import umc.spring.service.MemberMissionService.MemberMissionQueryService;
import umc.spring.validation.annotation.ChallengingMissions;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class ChallengingMissionValidator implements ConstraintValidator<ChallengingMissions, Long> {

    private final MemberMissionQueryService memberMissionQueryService;
    private final MemberMissionRepository memberMissionRepository;

    @Override
    public void initialize(ChallengingMissions constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if(memberMissionQueryService.findMemberMission(value) != null){
            MemberMission memberMission = memberMissionQueryService.findMemberMission(value);

            if(memberMission.getStatus() == MissionStatus.CHALLENGING){
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(ErrorStatus.INPROGRESS_MISSION.toString()).addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}