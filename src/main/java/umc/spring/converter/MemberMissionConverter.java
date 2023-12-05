package umc.spring.converter;

import org.springframework.data.domain.Page;
import umc.spring.domain.Member;
import umc.spring.domain.Mission;
import umc.spring.domain.Restaurant;
import umc.spring.domain.enums.MissionStatus;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.web.dto.MemberResponseDTO;
import umc.spring.web.dto.Mission.MissionResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public static MissionResponseDTO.MyChallengingMissionDTO myChallengingMissionDTO(MemberMission memberMission){
        return MissionResponseDTO.MyChallengingMissionDTO.builder()
                .restaurantName(memberMission.getMission().getRestaurant().getName())
                .content(memberMission.getMission().getContent())
                .deadLine(memberMission.getMission().getDeadline())
                .status(memberMission.getStatus().name())
                .reward(memberMission.getMission().getReward())
                .createdAt(memberMission.getMission().getCreatedAt().toLocalDate())
                .build();
    }

    public static MissionResponseDTO.MyChallengingMissionListDTO myChallengingMissionListDTO(Page<MemberMission> myMissionList){

        List<MissionResponseDTO.MyChallengingMissionDTO> myChallengingMissionDTOList = myMissionList.stream()
                .map(MemberMissionConverter::myChallengingMissionDTO).collect(Collectors.toList());

        return MissionResponseDTO.MyChallengingMissionListDTO.builder()
                .isFirst(myMissionList.isFirst())
                .isLast(myMissionList.isLast())
                .missionList(myChallengingMissionDTOList)
                .totalPage(myMissionList.getTotalPages())
                .totalElements(myMissionList.getTotalElements())
                .listSize(myChallengingMissionDTOList.size())
                .build();
    }
}
