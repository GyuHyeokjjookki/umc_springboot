package umc.spring.service.MissionService;

import umc.spring.domain.Mission;
import umc.spring.web.dto.Mission.MissionRequestDTO;
import umc.spring.web.dto.Mission.MissionResponseDTO;

public interface MissionCommandService {
    public Mission addMission(Long restaurantId, MissionRequestDTO.MissionDTO request);
}
