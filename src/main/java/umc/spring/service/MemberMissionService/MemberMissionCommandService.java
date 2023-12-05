package umc.spring.service.MemberMissionService;

import umc.spring.domain.mapping.MemberMission;

public interface MemberMissionCommandService {

    public MemberMission challengeMission(Long missionId, Long memberId);

    public MemberMission completeMission(Long missionId, Long memberId);
}
