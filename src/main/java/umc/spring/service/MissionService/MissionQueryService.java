package umc.spring.service.MissionService;

import org.springframework.data.domain.Page;
import umc.spring.domain.Mission;

public interface MissionQueryService {

    public boolean existMissionById(Long id);
    Page<Mission> getMissionList(Long restaurantId, Integer page);
}
