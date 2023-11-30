package umc.spring.service.MemberMissionService;

import umc.spring.domain.mapping.MemberMission;

import java.util.Optional;

public interface MemberMissionQueryService {
    MemberMission findMemberMission(Long missionId);
}
