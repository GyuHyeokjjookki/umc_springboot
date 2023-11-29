package umc.spring.converter;

import umc.spring.domain.Member;
import umc.spring.domain.Mission;
import umc.spring.domain.enums.MissionStatus;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.web.dto.MemberResponseDTO;

import java.time.LocalDateTime;

public class MemberMissionConverter {

    public static MemberResponseDTO.ChallengeMissionDTO toChallengMissionDTO(MemberMission mission){
        return MemberResponseDTO.ChallengeMissionDTO.builder()
                .memberMissionId(mission.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static MemberMission toChallengeMission(Member member, Mission mission){
        return MemberMission.builder()
                .member(member)
                .mission(mission)
                .status(MissionStatus.CHALLENGING)
                .build();
    }
}
