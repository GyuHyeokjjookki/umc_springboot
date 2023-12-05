package umc.spring.service.MemberMissionService;

import org.springframework.data.domain.Page;
import umc.spring.domain.Mission;
import umc.spring.domain.mapping.MemberMission;

import java.util.Optional;

public interface MemberMissionQueryService {
    MemberMission findMemberMissionByMissionId(Long missionId);

    public Page<MemberMission> getMyMissionList(Long memberId, Integer page);
}
