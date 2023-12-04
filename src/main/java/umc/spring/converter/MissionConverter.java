package umc.spring.converter;

import org.springframework.data.domain.Page;
import umc.spring.domain.Mission;
import umc.spring.domain.Restaurant;
import umc.spring.domain.Review;
import umc.spring.web.dto.Mission.MissionRequestDTO;
import umc.spring.web.dto.Mission.MissionResponseDTO;
import umc.spring.web.dto.ReviewRequestDTO;
import umc.spring.web.dto.ReviewResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MissionConverter {

    public static MissionResponseDTO.AddMissionDTO toAddMissionDTO(Mission mission){
        return MissionResponseDTO.AddMissionDTO.builder()
                .missionId(mission.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Mission toMission(MissionRequestDTO.MissionDTO request){
        return Mission.builder()
                .reward(request.getReward())
                .deadline(request.getDeadLine())
                .content(request.getContent())
                .build();
    }

    public static MissionResponseDTO.MissionPreViewDTO missionPreViewDTO(Mission mission){
        return MissionResponseDTO.MissionPreViewDTO.builder()
                .reward(mission.getReward())
                .deadLine(mission.getDeadline())
                .content(mission.getContent())
                .createdAt(mission.getCreatedAt().toLocalDate())
                .build();
    }

    public static MissionResponseDTO.MissionPreViewListDTO missionPreViewListDTO(Page<Mission> missionList, Optional<Restaurant> restaurant){

        List<MissionResponseDTO.MissionPreViewDTO> missionPreViewDTOList = missionList.stream()
                .map(MissionConverter::missionPreViewDTO).collect(Collectors.toList());

        return MissionResponseDTO.MissionPreViewListDTO.builder()
                .restaurantName(restaurant.get().getName())
                .isLast(missionList.isLast())
                .isFirst((missionList.isFirst()))
                .totalPage(missionList.getTotalPages())
                .totalElements(missionList.getTotalElements())
                .listSize(missionPreViewDTOList.size())
                .missionList(missionPreViewDTOList)
                .build();
    }
}
