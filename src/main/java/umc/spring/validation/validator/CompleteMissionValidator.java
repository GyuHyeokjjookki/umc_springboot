package umc.spring.validation.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.domain.enums.MissionStatus;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.service.MemberMissionService.MemberMissionQueryService;
import umc.spring.validation.annotation.AlreadyCompleteMission;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompleteMissionValidator implements ConstraintValidator<AlreadyCompleteMission, Long> {

    private final MemberMissionQueryService memberMissionQueryService;

    @Override
    public void initialize(AlreadyCompleteMission constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    @Transactional
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if(memberMissionQueryService.findMemberMissionByMissionId(value) != null){
            MemberMission memberMission = memberMissionQueryService.findMemberMissionByMissionId(value);

            if(memberMission.getStatus() == MissionStatus.COMPLETE){
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(ErrorStatus.ALREADY_COMPLETE_MISSION.toString()).addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
